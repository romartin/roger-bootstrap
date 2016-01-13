/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ${packageName};

import javax.annotation.Generated;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.roger600.uberfire.testapp.api.model.Definition;

/*
 * WARNING! This class is generated. Do not modify.
 */
@Generated("org.roger600.uberfire.testapp.processor.definition.DefinitionProcessor")
@Dependent
@Named("${identifier}")
public class ${className} implements ${realClassName} {

    // private final ${realClassName} realDefinition = new ${realClassName}();

    public ${className}() {}

    @Override
    public String getId() {
        return "${identifier}";
    }

    @Override
    public String getName() {
        return "${name}";
    }
    
}
