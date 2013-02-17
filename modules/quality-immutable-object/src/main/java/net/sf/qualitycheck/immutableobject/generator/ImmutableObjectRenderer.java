package net.sf.qualitycheck.immutableobject.generator;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Clazz;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

@ThreadSafe
final class ImmutableObjectRenderer {

	public static String toString(@Nonnull final Clazz clazz, @Nonnull final ImmutableSettings settings) {
		Check.notNull(clazz, "clazz");
		Check.notNull(settings, "settings");

		final STGroup group = new STGroupFile("templates/default.stg");
		group.registerRenderer(Field.class, new FieldRenderer(settings));
		group.registerRenderer(String.class, new BasicFormatRenderer());
		final ST template = group.getInstanceOf("immutableCompilationUnit");
		template.add("settings", settings);

		return SourceCodeFormatter.format(template.render());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private ImmutableObjectRenderer() {
		// This class is not intended to create objects from it.
	}

}
