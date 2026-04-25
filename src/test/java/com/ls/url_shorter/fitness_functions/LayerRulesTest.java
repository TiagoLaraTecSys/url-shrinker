package com.ls.url_shorter.fitness_functions;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class LayerRulesTest {

    @Test
    public void Services_should_only_be_accessed_by_Controllers() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ls.url_shorter");

        ArchRule myRule = classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

        myRule.check(importedClasses);
    }

    @Test
    public void Repository_should_only_be_accessed_by_Services() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.ls.url_shorter");

        ArchRule myRule = noClasses()
                .that().resideInAPackage("..controller..")
                .should().accessClassesThat().resideInAPackage( "..repository..");

        myRule.check(importedClasses);
    }
}