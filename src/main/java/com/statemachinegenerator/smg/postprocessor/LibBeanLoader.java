package com.statemachinegenerator.smg.postprocessor;

import com.bmeme.lib.libannotation.annotations.LibAction;
import com.bmeme.lib.libannotation.annotations.LibGuard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;

// ---- to study ----

@Slf4j
@Component
@RequiredArgsConstructor
public class LibBeanLoader {

    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;


    /*
        form doc:
            A component provider that provides candidate components from a base package.
            Can use the index if it is available of scans the classpath otherwise.
            Candidate components are identified by applying exclude and include filters.
            AnnotationTypeFilter, AssignableTypeFilter include filters on an annotation/superclass that are
            annotated with Indexed are supported: if any other include filter is specified,
            the index is ignored and classpath scanning is used instead.
     */

    private final ConfigurableListableBeanFactory configurableBeanFactory;

    @PostConstruct
    public void init() {
        findAnnotatedClasses("com.bmeme.lib");
    }

    public void findAnnotatedClasses(String scanPackage) {

        log.info("---- Register Lib beans ----");

        ClassPathScanningCandidateComponentProvider provider = createComponentScanner();

        for (BeanDefinition beanDef : provider.findCandidateComponents(scanPackage)) {
            loadBean(beanDef);
        }

    }

    private ClassPathScanningCandidateComponentProvider createComponentScanner() {

        // Don't pull default filters (@Component, etc.):
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(LibAction.class));
        provider.addIncludeFilter(new AnnotationTypeFilter(LibGuard.class));
        return provider;

    }

    private void loadBean(BeanDefinition beanDef) {

        try {
            Class<?> cl = Class.forName(beanDef.getBeanClassName());

            Constructor<?> ctr = cl.getConstructor();
            Object toRegister = ctr.newInstance();

            String beanName = String.valueOf(toRegister.hashCode());

            Object libBeanInstance = configurableBeanFactory.initializeBean(toRegister, beanName);
            configurableBeanFactory.autowireBeanProperties(libBeanInstance, AUTOWIRE_MODE, true);
            configurableBeanFactory.registerSingleton(beanName, libBeanInstance);

            log.info("Bean loaded: " + cl.getSimpleName() + " with name: " + beanName);

        } catch (Exception e) {
            System.err.println("Got exception: " + e);
        }

    }
}
