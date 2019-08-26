package com.statemachinegenerator.smg.postprocessor;

import com.bmeme.lib.libannotation.annotations.LibAction;
import com.bmeme.lib.libannotation.annotations.LibGuard;
import com.statemachinegenerator.smg.SmgApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;

// ---- to study ----

@Slf4j
@Component
//@RequiredArgsConstructor
public class LibBeanLoader {

    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE;

    private final ConfigurableListableBeanFactory configurableBeanFactory;
    private static RestartEndpoint restartEndpoint;

    @Autowired
    public LibBeanLoader(ConfigurableListableBeanFactory configurableBeanFactory, RestartEndpoint restartEndpoint){
        this.configurableBeanFactory = configurableBeanFactory;
        LibBeanLoader.restartEndpoint = restartEndpoint;
    }

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
        ClassPathScanningCandidateComponentProvider provider
                = new ClassPathScanningCandidateComponentProvider(false);
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

    public static void restart() {
        Thread restartThread = new Thread(() -> {
            try{
                Thread.sleep(2000);
            }catch(InterruptedException e){}
            restartEndpoint.restart();
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }

}
