package de.ninafoss.generator;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import de.ninafoss.generator.model.InstanceStateModel;
import de.ninafoss.generator.model.InstanceStatesModel;
import de.ninafoss.generator.templates.InstanceStateTemplate;
import de.ninafoss.generator.utils.Field;

@SupportedAnnotationTypes("de.ninafoss.generator.InstanceState")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class InstanceStateProcessor extends BaseProcessor {

	@Override
	public void process(RoundEnvironment environment) throws IOException {
		InstanceStatesModel instanceStates = new InstanceStatesModel();
		for (Element element : environment.getElementsAnnotatedWith(InstanceState.class)) {
			instanceStates.add(new Field(utils, (VariableElement) element));
		}
		for (InstanceStateModel model : (Iterable<InstanceStateModel>) (instanceStates.instanceStates()::iterator)) {
			generateInstanceStates(model);
		}
	}

	private void generateInstanceStates(InstanceStateModel model) throws IOException {
		JavaFileObject file = filer.createSourceFile(model.getJavaPackage() + ".InstanceStates", model.elements());
		Writer writer = file.openWriter();
		new InstanceStateTemplate(model).render(writer);
		writer.close();
	}

}
