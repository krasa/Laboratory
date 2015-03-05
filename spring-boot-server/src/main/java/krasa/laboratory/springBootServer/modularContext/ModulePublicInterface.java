package krasa.laboratory.springBootServer.modularContext;

/**
 * Marker interface signalizing, that class of this type could be used as public intefrace of particular module.
 * ModulePublicInterfacePostProcessor exports all beans with this marker to root application context, so they are
 * reachable by any other modules or web application contexts.
 */
public interface ModulePublicInterface {
}