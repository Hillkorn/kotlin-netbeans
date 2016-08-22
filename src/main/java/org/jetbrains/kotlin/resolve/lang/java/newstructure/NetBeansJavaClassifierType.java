/**
 * *****************************************************************************
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************
 */
package org.jetbrains.kotlin.resolve.lang.java.newstructure;

import java.util.Collections;
import java.util.List;
import javax.lang.model.type.TypeKind;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.lang.java.NBElementUtils;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.TypeMirrorHandle;
import org.netbeans.api.project.Project;

/**
 *
 * @author Alexander.Baratynski
 */
public class NetBeansJavaClassifierType extends NetBeansJavaType implements JavaClassifierType {

    public NetBeansJavaClassifierType(TypeMirrorHandle handle, Project project) {
        super(handle, project);
    }
    
    @Override
    public JavaClassifier getClassifier() {
        switch (getHandle().getKind()) {
            case DECLARED:
                ElementHandle elementHandle = ElementHandle.from(getHandle());
                return NetBeansJavaClassifier.create(new FqName(elementHandle.getQualifiedName()), 
                        getProject(), getHandle());
            case TYPEVAR:
                String fqName = NBElementUtils.getFqNameForTypeVariable(getHandle(), getProject());
                return NetBeansJavaClassifier.create(new FqName(fqName), 
                        getProject(), getHandle());
            default:
                return null;
        }
    }

    @Override
    public List<JavaType> getTypeArguments() {
        if (getHandle().getKind() == TypeKind.DECLARED){
            
            //TODO
            return null;
        } else return Collections.emptyList();
    }

    @Override
    public boolean isRaw() {
        if (getHandle().getKind() == TypeKind.DECLARED) {
            return NBElementUtils.isRaw(getProject(), getHandle());
        } else return true;
    }

    @Override
    public String getCanonicalText() {
        return getHandle().toString();
    }

    @Override
    public String getPresentableText() {
        return getHandle().toString();
    }
    
}