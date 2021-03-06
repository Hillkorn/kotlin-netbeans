@file:JvmName("RenamePerformer")
/*******************************************************************************
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
 *******************************************************************************/
package org.jetbrains.kotlin.refactorings.rename

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import java.io.File
import javax.swing.text.Position
import org.jetbrains.kotlin.descriptors.SourceElement
import org.jetbrains.kotlin.highlighter.occurrences.OccurrencesUtils
import org.jetbrains.kotlin.navigation.references.ReferenceUtils
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement
import org.jetbrains.kotlin.utils.ProjectUtils
import org.netbeans.api.project.Project
import org.netbeans.modules.csl.api.OffsetRange
import org.netbeans.modules.csl.spi.GsfUtilities
import org.openide.filesystems.FileObject
import org.openide.filesystems.FileUtil
import org.openide.text.CloneableEditorSupport
import org.openide.text.PositionBounds
import org.openide.text.PositionRef

/*
  @author Alexander.Baratynski
  Created on Sep 13, 2016
*/

fun getRenameRefactoringMap(fo: FileObject, psi: PsiElement, newName: String): Map<FileObject, List<OffsetRange>> {
    val ranges = hashMapOf<FileObject, List<OffsetRange>>()
    
    val ktElement: KtElement? = PsiTreeUtil.getNonStrictParentOfType(psi, KtElement::class.java)
    if (ktElement == null) return ranges
    
    val sourceElements = ReferenceUtils.resolveToSourceDeclaration(ktElement)
    if (sourceElements.isEmpty()) return ranges
     
    val searchingElements = OccurrencesUtils.getSearchingElements(sourceElements)
    val project = ProjectUtils.getKotlinProjectForFileObject(fo)
    if (project == null) return ranges
    
    ProjectUtils.getSourceFiles(project).forEach { 
        val occurrencesRanges = OccurrencesUtils.search(searchingElements, it)
        val f = File(it.virtualFile.path)
        val file = FileUtil.toFileObject(f)
        if (file != null) ranges.put(file, occurrencesRanges)
    }
    
    return ranges
}

fun createPositionBoundsForFO(fo: FileObject, ranges: List<OffsetRange>): List<PositionBounds> {
    val bounds = arrayListOf<PositionBounds>()
    
    val ces = GsfUtilities.findCloneableEditorSupport(fo)
    if (ces == null) return emptyList()
    
    ranges.forEach{ 
        val startRef = ces.createPositionRef(it.start, Position.Bias.Forward)
        val endRef = ces.createPositionRef(it.end, Position.Bias.Forward)
        bounds.add(PositionBounds(startRef, endRef))
    }
    
    return bounds
}