package help.lixin.framework.power.meta.integration.plugin;

import java.util.ArrayList;
import java.util.List;

import help.lixin.framework.power.meta.integration.context.PowerMetaContext;

/**
 * 责任链
 * 
 * @author lixin
 *
 */
public class PluginChain implements Plugin {
	private List<Plugin> plugins = new ArrayList<Plugin>();

	public PluginChain addPlugin(Plugin plugin) {
		plugins.add(plugin);
		return this;
	}

	public void setPlugins(List<Plugin> plugins) {
		if (null != plugins) {
			this.plugins = plugins;
		}
	}

	public List<Plugin> getPlugins() {
		return plugins;
	}

	@Override
	public void apply(PowerMetaContext context) {
		for (Plugin plugin : plugins) {
			plugin.apply(context);
		}
	}
}
