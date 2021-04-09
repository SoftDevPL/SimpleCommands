package wg.simple.simplecommands.managers.guiengine.interfaces;

import java.util.List;

public interface RefreshFunction<T> {
    List<? extends T> getList();
}
