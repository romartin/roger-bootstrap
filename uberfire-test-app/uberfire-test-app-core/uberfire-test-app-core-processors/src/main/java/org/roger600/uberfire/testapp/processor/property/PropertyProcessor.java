package org.roger600.uberfire.testapp.processor.property;

import org.uberfire.annotations.processors.AbstractErrorAbsorbingProcessor;
import org.uberfire.annotations.processors.exceptions.GenerationException;

import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @see org.uberfire.annotations.processors.WorkbenchScreenProcessor
 */
@SupportedAnnotationTypes(PropertyProcessor.ANNOTATION_PROPERTY)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PropertyProcessor extends AbstractErrorAbsorbingProcessor {
    
    public static final String ANNOTATION_PROPERTY = "org.roger600.uberfire.testapp.api.model.annotation.property.Property";
    public static final String ANNOTATION_PROPERTY_NAME = "org.roger600.uberfire.testapp.api.model.annotation.property.PropertyName";
    public static final String ANNOTATION_DEFAULT_VALUE = "org.roger600.uberfire.testapp.api.model.annotation.property.DefaultValue";

    private final PropertyGenerator propertyGenerator;

    public PropertyProcessor() {
        PropertyGenerator pg = null;
        try {
            pg = new PropertyGenerator();
        } catch (Throwable t) {
            rememberInitializationError(t);
        }
        propertyGenerator = pg;
    }

    @Override
    protected boolean processWithExceptions(Set<? extends TypeElement> set, RoundEnvironment roundEnv) throws Exception {

        //We don't have any post-processing
        if ( roundEnv.processingOver() ) {
            return false;
        }

        //If prior processing threw an error exit
        if ( roundEnv.errorRaised() ) {
            return false;
        }

        final Messager messager = processingEnv.getMessager();
        final Elements elementUtils = processingEnv.getElementUtils();

        for ( Element e : roundEnv.getElementsAnnotatedWith( elementUtils.getTypeElement(ANNOTATION_PROPERTY) ) ) {
            final boolean isIface = e.getKind() == ElementKind.INTERFACE;
            final boolean isClass = e.getKind() == ElementKind.CLASS;
            if (isIface || isClass) {

                TypeElement classElement = (TypeElement) e;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                messager.printMessage(Diagnostic.Kind.NOTE, "Discovered property class [" + classElement.getSimpleName() + "]");

                final String packageName = packageElement.getQualifiedName().toString();
                final String classNameActivity = classElement.getSimpleName() + "Property";

                try {
                    //Try generating code for each required class
                    messager.printMessage( Diagnostic.Kind.NOTE, "Generating code for [" + classNameActivity + "]" );

                    final StringBuffer propertyClassCode = propertyGenerator.setConfig(new PropertyGenerator.PropertyGeneratorConfig(isIface)).generate( packageName,
                            packageElement,
                            classNameActivity,
                            classElement,
                            processingEnv );
                    
                    writeCode( packageName,
                            classNameActivity,
                            propertyClassCode );
                    
                } catch ( GenerationException ge ) {
                    final String msg = ge.getMessage();
                    processingEnv.getMessager().printMessage( Diagnostic.Kind.ERROR, msg, classElement );
                }
                
            }
        }
        
        return true;
    }


}
