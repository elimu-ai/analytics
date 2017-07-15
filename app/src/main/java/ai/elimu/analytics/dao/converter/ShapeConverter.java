package ai.elimu.analytics.dao.converter;

import android.util.Log;

import org.greenrobot.greendao.converter.PropertyConverter;
import ai.elimu.model.enums.content.Shape;

public class ShapeConverter implements PropertyConverter<Shape, String> {

    @Override
    public Shape convertToEntityProperty(String databaseValue) {
        Log.i(getClass().getName(), "convertToEntityProperty");

        Shape entityProperty = Shape.valueOf(databaseValue);
        Log.i(getClass().getName(), "entityProperty: " + entityProperty);
        return entityProperty;
    }

    @Override
    public String convertToDatabaseValue(Shape entityProperty) {
        Log.i(getClass().getName(), "convertToDatabaseValue");

        String databaseValue = entityProperty.toString();
        Log.i(getClass().getName(), "databaseValue: " + databaseValue);
        return databaseValue;
    }
}
