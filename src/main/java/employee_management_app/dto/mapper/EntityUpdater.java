package employee_management_app.dto.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityUpdater {
	
	private static Logger logger;

	public static <T> void updateIfNotNull(T existing, T updated) {
//		Validation to prevent null parameters
		if (existing == null || updated == null) {
			throw new IllegalArgumentException("Both entities must not be null");
		}
		
		Class<?> clazz = existing.getClass();
		logger = LoggerFactory.getLogger(clazz);
		
//		Keep track of what was updated for Logging or auditing
		Set<String> updatedFields = new HashSet<>();
		
		for (Field field: clazz.getDeclaredFields()) {
			try {
//				Skip fields we don't want to update (like 'id' or 'createdAt')
				if (shouldSkipField(field)) {
					continue;
				}
				
//				Get the field value from the updated customer
				String getterName = "get" + field.getName().substring(0, 1).toUpperCase() +
						field.getName().substring(1);
				Method getter = clazz.getMethod(getterName); 
				Object updatedValue = getter.invoke(updated);
				
//				Get the current value for comparison
				Object existingValue = getter.invoke(existing);
				
//				Only update if the new value is not null and different from the current value
				if (updatedValue != null && !Objects.equals(updatedValue, existingValue)) {
					String setterName = "set" + field.getName().substring(0, 1).toUpperCase() +
							field.getName().substring(1);
					Method setter = clazz.getMethod(setterName, field.getType());
					setter.invoke(existing, updatedValue);
					
//					Record that this field was updated
					updatedFields.add(field.getName());
				}
			} catch (Exception e) {
				logger.error("Error udpating field: " + field.getName(), e);
			}
//			Log updates with entity identification
			if (!updatedFields.isEmpty()) {
//				Try to get ID using common Id field names
				Object entityId = getEntityId(existing);
				String entityName = clazz.getSimpleName();
				
				if (entityId != null) {
					logger.info("Updated fields for {} with ID {}: {}",
							entityName, entityId, String.join(", ", updatedFields));
				} else {
					logger.info("Updated fields for {} instance: {}",
							entityName, String.join(", ", updatedFields));
				}
			}
		}
	}
	
	private static boolean shouldSkipField(Field field) {
//		Skip certain fields that shouldn't be updated
		Set<String> skipFields = Set.of("id", "createdAt", "createdBy");
		return skipFields.contains(field.getName()) || 
				field.isSynthetic() ||
				Modifier.isStatic(field.getModifiers());
	}
	
	private static Object getEntityId(Object entity) {
//		Common ID field names
		String[] possibleIdFields = {"id", "ID", "Id", "entityId"};
		
		for (String idField : possibleIdFields) {
			try {
				String getterName = "get" + idField.substring(0, 1).toUpperCase() +
						idField.substring(1);
				Method getter = entity.getClass().getMethod(getterName);
				Object id = getter.invoke(entity);
				if (id != null) {
					return id;
				}
			} catch (Exception ignored) {
//				Continue trying other possible ID fields
			}
		}
		return null;
	}
}
