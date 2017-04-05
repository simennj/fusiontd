package no.fusiontd;

import com.badlogic.ashley.core.Component;

public interface CloneableComponent<T extends CloneableComponent<T>> extends Component {
    T cloneComponent();
}
