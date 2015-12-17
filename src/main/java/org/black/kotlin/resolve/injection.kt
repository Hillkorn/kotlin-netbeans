package org.black.kotlin.resolve

import org.jetbrains.kotlin.frontend.java.di.ContainerForTopDownAnalyzerForJvm
import org.jetbrains.kotlin.context.ModuleContext
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.load.java.lazy.SingleModuleClassResolver
import org.jetbrains.kotlin.container.*
import org.jetbrains.kotlin.frontend.di.*
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.resolve.lazy.ResolveSession
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzerForTopLevel
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver
import org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJava
import org.jetbrains.kotlin.load.kotlin.JvmVirtualFileFinderFactory
import org.jetbrains.kotlin.load.java.JavaClassFinderImpl
import org.jetbrains.kotlin.load.java.components.TraceBasedExternalSignatureResolver
import org.jetbrains.kotlin.load.java.components.LazyResolveBasedCache
import org.jetbrains.kotlin.load.java.components.TraceBasedErrorReporter
import org.jetbrains.kotlin.load.java.components.PsiBasedExternalAnnotationResolver
import org.jetbrains.kotlin.load.java.structure.impl.JavaPropertyInitializerEvaluatorImpl
import org.jetbrains.kotlin.load.java.sam.SamConversionResolverImpl
import org.jetbrains.kotlin.load.java.components.JavaSourceElementFactoryImpl
import org.jetbrains.kotlin.resolve.jvm.JavaLazyAnalyzerPostConstruct
import org.jetbrains.kotlin.load.java.JavaFlexibleTypeCapabilitiesProvider
import org.jetbrains.kotlin.resolve.jvm.JavaClassFinderPostConstruct
import org.jetbrains.kotlin.resolve.lazy.FileScopeProviderImpl
import org.jetbrains.kotlin.resolve.BodyResolveCache
import org.jetbrains.kotlin.synthetic.AdditionalScopesWithJavaSyntheticExtensions
import org.jetbrains.kotlin.incremental.components.LookupTracker
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatform
import org.jetbrains.kotlin.resolve.CompilerEnvironment
import org.jetbrains.kotlin.descriptors.PackagePartProvider

fun StorageComponentContainer.configureJavaTopDownAnalysis(moduleContentScope: GlobalSearchScope, 
        project: Project, lookupTracker: LookupTracker) {
    useInstance(moduleContentScope)
    useInstance(lookupTracker)

    useImpl<ResolveSession>()

    useImpl<LazyTopDownAnalyzer>()
    useImpl<LazyTopDownAnalyzerForTopLevel>()
    useImpl<JavaDescriptorResolver>()
    useImpl<DeserializationComponentsForJava>()

    useInstance(JvmVirtualFileFinderFactory.SERVICE.getInstance(project).create(moduleContentScope))

    useImpl<TraceBasedExternalSignatureResolver>()
    useImpl<TraceBasedErrorReporter>()
    
    useInstance(SamConversionResolverImpl)
    useImpl<JavaLazyAnalyzerPostConstruct>()
    useImpl<JavaFlexibleTypeCapabilitiesProvider>()
    useImpl<AdditionalScopesWithJavaSyntheticExtensions>()
}/*

package org.jetbrains.kotlin.frontend.java.di

import org.jetbrains.kotlin.frontend.java.di.ContainerForTopDownAnalyzerForJvm
import org.jetbrains.kotlin.context.ModuleContext
import org.jetbrains.kotlin.resolve.BindingTrace
import org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.load.java.lazy.SingleModuleClassResolver
import org.jetbrains.kotlin.container.*
import org.jetbrains.kotlin.frontend.di.*
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.resolve.lazy.ResolveSession
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer
import org.jetbrains.kotlin.resolve.LazyTopDownAnalyzerForTopLevel
import org.jetbrains.kotlin.resolve.jvm.JavaDescriptorResolver
import org.jetbrains.kotlin.load.kotlin.DeserializationComponentsForJava
import org.jetbrains.kotlin.load.kotlin.JvmVirtualFileFinderFactory
import org.jetbrains.kotlin.load.java.JavaClassFinderImpl
import org.jetbrains.kotlin.load.java.components.TraceBasedExternalSignatureResolver
import org.jetbrains.kotlin.load.java.components.LazyResolveBasedCache
import org.jetbrains.kotlin.load.java.components.TraceBasedErrorReporter
import org.jetbrains.kotlin.load.java.components.PsiBasedExternalAnnotationResolver
import org.jetbrains.kotlin.load.java.structure.impl.JavaPropertyInitializerEvaluatorImpl
import org.jetbrains.kotlin.load.java.sam.SamConversionResolverImpl
import org.jetbrains.kotlin.load.java.components.JavaSourceElementFactoryImpl
import org.jetbrains.kotlin.resolve.jvm.JavaLazyAnalyzerPostConstruct
import org.jetbrains.kotlin.load.java.JavaFlexibleTypeCapabilitiesProvider
import org.jetbrains.kotlin.resolve.jvm.JavaClassFinderPostConstruct

//import org.netbeans.api.project.Project
import org.jetbrains.kotlin.resolve.lazy.FileScopeProviderImpl
import org.jetbrains.kotlin.resolve.BodyResolveCache
import org.jetbrains.kotlin.synthetic.AdditionalScopesWithJavaSyntheticExtensions
import org.jetbrains.kotlin.incremental.components.LookupTracker
import org.jetbrains.kotlin.resolve.jvm.platform.JvmPlatform
import org.jetbrains.kotlin.resolve.CompilerEnvironment
import org.jetbrains.kotlin.descriptors.PackagePartProvider

public fun StorageComponentContainer.configureJavaTopDownAnalysis(moduleContentScope: GlobalSearchScope, project: Project, lookupTracker: LookupTracker) {
        useInstance(moduleContentScope)
        useInstance(lookupTracker)
        useImpl<ResolveSession>()

        useImpl<LazyTopDownAnalyzer>()
        useImpl<LazyTopDownAnalyzerForTopLevel>()
        useImpl<JavaDescriptorResolver>()
        useImpl<DeserializationComponentsForJava>()

        useInstance(JvmVirtualFileFinderFactory.SERVICE.getInstance(project).create(moduleContentScope))

        useImpl<JavaClassFinderImpl>()
        useImpl<TraceBasedExternalSignatureResolver>()
        useImpl<LazyResolveBasedCache>()
        useImpl<TraceBasedErrorReporter>()
        useImpl<PsiBasedExternalAnnotationResolver>()
        useImpl<JavaPropertyInitializerEvaluatorImpl>()
        useInstance(SamConversionResolverImpl)
        useImpl<JavaSourceElementFactoryImpl>()
        useImpl<JavaLazyAnalyzerPostConstruct>()
        useImpl<JavaFlexibleTypeCapabilitiesProvider>()
        useImpl<AdditionalScopesWithJavaSyntheticExtensions>()
    }

 private fun StorageComponentContainer.javaAnalysisInit() {
        get<JavaClassFinderImpl>().initialize()
        get<JavaClassFinderPostConstruct>().postCreate()
    }

public class ContainerForTopDownAnalyzerForJvm(container: StorageComponentContainer) {
    val lazyTopDownAnalyzerForTopLevel: LazyTopDownAnalyzerForTopLevel by container
    val javaDescriptorResolver: JavaDescriptorResolver by container
    val deserializationComponentsForJava: DeserializationComponentsForJava by container
}


public fun createContainerForTopDownAnalyzerForJvm(
        moduleContext: ModuleContext, bindingTrace: BindingTrace,
        declarationProviderFactory: DeclarationProviderFactory,
        moduleContentScope: GlobalSearchScope,
        nbProject: org.netbeans.api.project.Project,
        lookupTracker: LookupTracker,
        packagePartProvider: PackagePartProvider
): Pair<ContainerForTopDownAnalyzerForJvm, StorageComponentContainer> = createContainer("TopDownAnalyzerForJvm") {
    useInstance(packagePartProvider)
    
    configureModule(moduleContext, JvmPlatform, bindingTrace)
    configureJavaTopDownAnalysis(moduleContentScope, moduleContext.project, lookupTracker)
    
    useInstance(nbProject)
    useInstance(declarationProviderFactory)
    
    CompilerEnvironment.configure(this)

    useImpl<SingleModuleClassResolver>()
    useImpl<FileScopeProviderImpl>()
}.let {
    it.javaAnalysisInit()

    Pair(ContainerForTopDownAnalyzerForJvm(it), it)
}