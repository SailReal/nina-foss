package de.ninafoss.generator;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import de.ninafoss.generator.model.CallbackModel;
import de.ninafoss.generator.model.CallbacksModel;
import de.ninafoss.generator.model.CallbacksModel.CallbacksClassModel;
import de.ninafoss.generator.templates.CallbacksTemplate;
import de.ninafoss.generator.utils.Method;

@SupportedAnnotationTypes("de.ninafoss.generator.Callback")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CallbackProcessor extends BaseProcessor {

	@Override
	public void process(RoundEnvironment environment) throws IOException {
		CallbacksModel callbacksModel = new CallbacksModel();
		for (Element element : environment.getElementsAnnotatedWith(Callback.class)) {
			try {
				CallbackModel callbackModel = new CallbackModel(new Method(utils, (ExecutableElement) element));
				callbacksModel.add(callbackModel);
			} catch (ProcessorException e) {
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.getElement());
			}
		}
		for (CallbacksClassModel packageWithCallbacks : callbacksModel.getCallbacksClasses()) {
			generateCallbacks(packageWithCallbacks);
		}
	}

	private void generateCallbacks(CallbacksClassModel callbacksClassModel) throws IOException {
		JavaFileObject file = filer.createSourceFile(callbacksClassModel.getJavaPackage() + '.' + callbacksClassModel.getCallbacksClassName());
		Writer writer = file.openWriter();
		new CallbacksTemplate(callbacksClassModel).render(writer);
		writer.close();
	}

}
