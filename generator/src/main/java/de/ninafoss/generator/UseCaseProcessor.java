package de.ninafoss.generator;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import de.ninafoss.generator.model.UseCaseModel;
import de.ninafoss.generator.templates.UseCaseTemplate;

@SupportedAnnotationTypes("de.ninafoss.generator.UseCase")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class UseCaseProcessor extends BaseProcessor {

	@Override
	public void process(RoundEnvironment environment) throws IOException {
		for (Element element : environment.getElementsAnnotatedWith(UseCase.class)) {
			generateUseCase((TypeElement) element);
		}
	}

	private void generateUseCase(TypeElement element) throws IOException {
		UseCaseModel model = new UseCaseModel(utils.type(element));
		JavaFileObject file = filer.createSourceFile(model.getClassName(), element);
		Writer writer = file.openWriter();
		new UseCaseTemplate(model).render(writer);
		writer.close();
	}

}
