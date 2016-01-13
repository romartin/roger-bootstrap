package org.roger600.uberfire.testapp.processor.definition;

import org.roger600.uberfire.testapp.api.model.annotation.definition.IsProperty;
import org.roger600.uberfire.testapp.processor.GeneratorUtils;
import org.uberfire.annotations.processors.AbstractGenerator;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.uberfire.relocated.freemarker.template.Template;
import org.uberfire.relocated.freemarker.template.TemplateException;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * see http://www.pingtimeout.fr/2012/10/debugging-annotation-processor-in-every.html
 */
public class DefinitionGenerator extends AbstractGenerator  {
    
    public class PropertyElement {
        private final String propertyClassName;
        private final String methodName;

        public PropertyElement(String propertyClassName, String methodName) {
            this.propertyClassName = propertyClassName;
            this.methodName = methodName;
        }

        public String getPropertyClassName() {
            return propertyClassName;
        }

        public String getMethodName() {
            return methodName;
        }
    }
    
    @Override
    public StringBuffer generate(String packageName, PackageElement packageElement, String className, Element element, ProcessingEnvironment processingEnvironment) throws GenerationException {

        final Messager messager = processingEnvironment.getMessager();
        print( messager, "Starting code generation for [" + className + "]" );

        final Elements elementUtils = processingEnvironment.getElementUtils();

        //Extract required information
        final TypeElement classElement = (TypeElement) element;
        final String annotationName = DefinitionProcessor.ANNOTATION_DEFINITION;

        String identifier = getAnnotationStringField(classElement, annotationName, "identifier");
        print( messager, "************** IDENTIFIER is " + identifier);
        String name = getAnnotationStringField(classElement, annotationName, "name");
        print( messager, "************** NAME is " + name);
        final TypeMirror propertyTypeMirror = elementUtils.getTypeElement( "org.roger600.uberfire.testapp.api.model.property.Property" ).asType();
        final List<ExecutableElement> propertyElements = GeneratorUtils.getAnnotatedMethods(classElement, processingEnvironment,
                DefinitionProcessor.ANNOTATION_IS_PROPERTY, propertyTypeMirror, new String[] {});
        
        final List<PropertyElement> properties = new LinkedList<>();
        if ( null != propertyElements && !propertyElements.isEmpty() ) {
            
            for (ExecutableElement executableElement : propertyElements) {

                final String methodName = executableElement.getSimpleName().toString();
                String returnClassName = null;
                final TypeMirror returnTypeMirror = executableElement.getReturnType();
                TypeKind returnKind = returnTypeMirror.getKind();
                if (returnKind == TypeKind.DECLARED) {
                    DeclaredType declaredReturnType = (DeclaredType) returnTypeMirror;
                    returnClassName = declaredReturnType.toString();
                }

                properties.add(new PropertyElement(returnClassName, methodName));
                
                print( messager, "[" + className + "] - Found property [class=" + returnClassName + "] at method [" + methodName + "].");
            }
            
        } else {
            print( messager, "[INFO] NO properties for definition " + className);
        }
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put( "packageName",
                packageName );
        root.put( "className",
                className );
        root.put( "realClassName",
                classElement.getSimpleName().toString() );
        root.put( "identifier",
                identifier );
        root.put( "defName",
                name );
        root.put( "properties",
                properties );
        
        /* root.put( "getNameMethodName",
                getNameMethodName ); */
        
        //Generate code
        final StringWriter sw = new StringWriter();
        final BufferedWriter bw = new BufferedWriter( sw );
        try {
            final Template template = config.getTemplate( "Definition.ftl" );
            template.process( root,
                    bw );
        } catch ( IOException ioe ) {
            throw new GenerationException( ioe );
        } catch ( TemplateException te ) {
            throw new GenerationException( te );
        } finally {
            try {
                bw.close();
                sw.close();
            } catch ( IOException ioe ) {
                throw new GenerationException( ioe );
            }
        }
        print( messager, "Successfully generated code for [" + className + "]" );

        return sw.getBuffer();

    }

    private String getAnnotationStringField(TypeElement classElement, String annotationName, String fieldName) {
        for (final AnnotationMirror am : classElement.getAnnotationMirrors()) {
            if (annotationName.equals(am.getAnnotationType().toString())) {
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet()) {
                    AnnotationValue aval = entry.getValue();
                    if (fieldName.equals(entry.getKey().getSimpleName().toString())) {
                        return aval.getValue().toString();
                    }
                }
            }
        }
        return null;
    }        
        
    private void print(final Messager messager , String message ) {
        messager.printMessage( Diagnostic.Kind.ERROR, message );

    }
}
