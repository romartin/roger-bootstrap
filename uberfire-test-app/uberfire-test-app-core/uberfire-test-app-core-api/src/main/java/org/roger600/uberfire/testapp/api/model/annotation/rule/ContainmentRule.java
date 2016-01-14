package org.roger600.uberfire.testapp.api.model.annotation.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) @Retention(RetentionPolicy.CLASS)
public @interface ContainmentRule {

    String identifier();
    
    String role();
    
    String[] permittedRoles();

}
