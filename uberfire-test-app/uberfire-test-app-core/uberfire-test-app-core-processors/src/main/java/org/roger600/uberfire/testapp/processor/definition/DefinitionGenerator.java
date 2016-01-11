package org.roger600.uberfire.testapp.processor.definition;

import org.roger600.uberfire.testapp.processor.GeneratorUtils;
import org.uberfire.annotations.processors.AbstractGenerator;
import org.uberfire.annotations.processors.exceptions.GenerationException;
import org.uberfire.relocated.freemarker.template.Template;
import org.uberfire.relocated.freemarker.template.TemplateException;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DefinitionGenerator extends AbstractGenerator  {
    
    @Override
    public StringBuffer generate(String packageName, PackageElement packageElement, String className, Element element, ProcessingEnvironment processingEnvironment) throws GenerationException {

        final Messager messager = processingEnvironment.getMessager();
        messager.printMessage( Diagnostic.Kind.NOTE, "Starting code generation for [" + className + "]" );

        final Elements elementUtils = processingEnvironment.getElementUtils();

        //Extract required information
        final TypeElement classElement = (TypeElement) element;
        final String annotationName = DefinitionProcessor.ANNOTATION_DEFINITION;

        String identifier = null;

        for ( final AnnotationMirror am : classElement.getAnnotationMirrors() ) {
            if ( annotationName.equals( am.getAnnotationType().toString() ) ) {
                for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet() ) {
                    AnnotationValue aval = entry.getValue();
                    if ( "identifier".equals( entry.getKey().getSimpleName().toString() ) ) {
                        identifier = aval.getValue().toString();
                    }
                }
                break;
            }
        }

        final String getNameMethodName = GeneratorUtils.getStringMethodName( classElement, DefinitionProcessor.ANNOTATION_DEFINITION_NAME, processingEnvironment );

        // Validations.
        if ( getNameMethodName == null ) {
            throw new GenerationException( "The Definition must provide a @DefinitionName annotated method to return a java.lang.String.", packageName + "." + className );
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
        root.put( "getNameMethodName",
                getNameMethodName );
        
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
        messager.printMessage( Diagnostic.Kind.NOTE, "Successfully generated code for [" + className + "]" );

        return sw.getBuffer();

    }

}
