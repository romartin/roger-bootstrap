package org.roger600.uberfire.testapp.processor.property;

import org.roger600.uberfire.testapp.api.model.annotation.property.DefaultValue;
import org.roger600.uberfire.testapp.processor.GeneratorUtils;
import org.roger600.uberfire.testapp.processor.ProcessingElement;
import org.uberfire.annotations.processors.AbstractGenerator;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.uberfire.relocated.freemarker.template.Template;
import org.uberfire.relocated.freemarker.template.TemplateException;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see org.uberfire.annotations.processors.ScreenActivityGenerator
 * 
 * links:
 * - https://docs.oracle.com/javase/tutorial/java/annotations/index.html
 * - http://www.pingtimeout.fr/2012/10/debugging-annotation-processor-in-every.html
 * - http://stackoverflow.com/questions/1458535/which-types-can-be-used-for-java-annotation-members
 * 
 * TODO: After all known field/annotations processes on the element, process the missing fieldss (not processed ones) by adding all them into the generated impl.
 */
public class PropertyGenerator extends AbstractGenerator  {

    public static class PropertyGeneratorConfig {
        
        // Iface or abstract class.
        private final boolean isInterface;

        public PropertyGeneratorConfig(boolean isInterface) {
            this.isInterface = isInterface;
        }

        public boolean isInterface() {
            return isInterface;
        }
    }

    private PropertyGeneratorConfig generatorConfig;

    public PropertyGenerator setConfig(PropertyGeneratorConfig config) {
        this.generatorConfig = config;
        return this;
    }
    
    @Override
    public StringBuffer generate(String packageName, PackageElement packageElement, String className, Element element, ProcessingEnvironment processingEnvironment) throws GenerationException {

        final Messager messager = processingEnvironment.getMessager();
        messager.printMessage( Diagnostic.Kind.NOTE, "Starting code generation for [" + className + "]" );

        final Elements elementUtils = processingEnvironment.getElementUtils();

        //Extract required information
        final TypeElement classElement = (TypeElement) element;
        final String annotationName = PropertyProcessor.ANNOTATION_PROPERTY;

        String identifier = null;
        String name = null;

        for ( final AnnotationMirror am : classElement.getAnnotationMirrors() ) {
            if ( annotationName.equals( am.getAnnotationType().toString() ) ) {
                for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet() ) {
                    AnnotationValue aval = entry.getValue();
                    if ( "identifier".equals( entry.getKey().getSimpleName().toString() ) ) {
                        identifier = aval.getValue().toString();
                    } else if ( "name".equals( entry.getKey().getSimpleName().toString() ) ) {
                        name = aval.getValue().toString();
                    }
                }
                break;
            }
        }
        
        
        // Field types.
        ProcessingElement defaultValueElement = null;
        List<VariableElement> variableElements = ElementFilter.fieldsIn( classElement.getEnclosedElements() );
        for (VariableElement variableElement : variableElements) {
            
            // Default Value.
            if ( GeneratorUtils.getAnnotation( elementUtils, variableElement, PropertyProcessor.ANNOTATION_DEFAULT_VALUE ) != null ) {
                final TypeMirror fieldReturnType = variableElement.asType();
                final String fieldReturnTypeName = GeneratorUtils.getTypeMirrorDeclaredName(fieldReturnType);
                final String fieldName = variableElement.getSimpleName().toString();
                defaultValueElement = new ProcessingElement(fieldReturnTypeName, fieldName);
            }
            
        }
        
        Map<String, Object> root = new HashMap<String, Object>();
        root.put( "packageName",
                packageName );
        root.put( "className",
                className );
        root.put( "classHierarchyModifier",
                generatorConfig.isInterface ? "implements" : "extends" );
        root.put( "realClassName",
                classElement.getSimpleName().toString() );
        root.put( "identifier",
                identifier );
        root.put( "name",
                name );
        root.put( "defaultValue",
                defaultValueElement );
        
        /*root.put( "getNameMethodName",
                getNameMethodName );*/
        
        //Generate code
        final StringWriter sw = new StringWriter();
        final BufferedWriter bw = new BufferedWriter( sw );
        try {
            final Template template = config.getTemplate( "Property.ftl" );
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
        messager.printMessage( Diagnostic.Kind.NOTE, "Successfully generated code for [" + className + "]" );

        return sw.getBuffer();

    }

}
