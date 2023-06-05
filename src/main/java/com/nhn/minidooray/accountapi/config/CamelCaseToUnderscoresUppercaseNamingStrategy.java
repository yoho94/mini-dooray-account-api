package com.nhn.minidooray.accountapi.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CamelCaseToUnderscoresUppercaseNamingStrategy extends
    CamelCaseToUnderscoresNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return toUppercase(super.toPhysicalCatalogName(name, jdbcEnvironment));
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return toUppercase(super.toPhysicalSchemaName(name, jdbcEnvironment));
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return toUppercase(super.toPhysicalTableName(name, jdbcEnvironment));
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return toUppercase(super.toPhysicalSequenceName(name, jdbcEnvironment));
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return toUppercase(super.toPhysicalColumnName(name, jdbcEnvironment));
    }

    private Identifier toUppercase(Identifier name) {
        if (name == null) {
            return null;
        }

        return new Identifier(name.getText().toUpperCase(), name.isQuoted());
    }
}
