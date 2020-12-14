package com.github.rkharisov.task1;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Tree {

    private Set<Tree> childs;

    public Tree(Collection<Tree> childs) {
        this.setChilds(childs);
    }

    public Tree(Tree ... tries) {
        this(Set.of(tries));
    }

    public Tree() {
        this(Set.of());
    }

    public int getHeight() {
        return childs.stream()
                .mapToInt(t -> t.getHeight() + 1)
                .max()
                .orElse(0);
    }

    public void setChilds(Collection<Tree> tries) {
        if (tries == null) {
            throw new NullPointerException("Child nodes can not be null");
        }
        this.childs = new HashSet<>(tries);
    }

}
