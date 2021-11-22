package de.ninafoss.generator.model;

import java.util.Optional;

import de.ninafoss.generator.ProcessorException;
import de.ninafoss.generator.utils.Field;
import de.ninafoss.generator.utils.Type;

public class FragmentModel implements Comparable<FragmentModel> {

	private final String qualifiedName;

	private final boolean hasPresenter;
	private final String presenterFieldName;
	private final String presenterQualifiedName;

	public FragmentModel(Type type) {
		try {
			this.qualifiedName = type.qualifiedName();
			Optional<Field> presenterField = presenterField(type);
			hasPresenter = presenterField.isPresent();
			presenterFieldName = presenterField.map(Field::name).orElse(null);
			presenterQualifiedName = presenterField.map(Field::type).map(Type::qualifiedName).orElse(null);
		} catch (RuntimeException e) {
			throw new ProcessorException(e.getMessage(), type.element());
		}
	}

	private static Optional<Field> presenterField(Type type) {
		return type.fields().filter(field -> field.type() != null && field.type().isAssignableTo("de.ninafoss.presentation.presenter.Presenter")).findFirst();
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public String getPresenterFieldName() {
		return presenterFieldName;
	}

	public String getPresenterQualifiedName() {
		return presenterQualifiedName;
	}

	public boolean isHasPresenter() {
		return hasPresenter;
	}

	@Override
	public int compareTo(FragmentModel fragmentModel) {
		return this.qualifiedName.compareTo(fragmentModel.qualifiedName);
	}
}
