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
package org.jetbrains.kotlin.indexer

import org.netbeans.modules.parsing.api.Snapshot
import org.netbeans.modules.parsing.spi.indexing.Context
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexer
import org.netbeans.modules.parsing.spi.indexing.EmbeddingIndexerFactory
import org.netbeans.modules.parsing.spi.indexing.Indexable

/*

  @author Alexander.Baratynski
  Created on Sep 9, 2016
*/

class KotlinIndexerFactory : EmbeddingIndexerFactory() {
    override fun createIndexer(indexable: Indexable, snapshot : Snapshot) = KotlinIndexer()
    
    override fun filesDeleted(deleted : Iterable<Indexable>, context : Context) {}
    override fun filesDirty(dirty : Iterable<Indexable>, context : Context) {}
    
    override fun getIndexerName() = "Kotlin Indexer"
    override fun getIndexVersion() = 1
}