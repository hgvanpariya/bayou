package edu.rice.bayou.dom_driver;

import edu.rice.bayou.dsl.DLoop;
import edu.rice.bayou.dsl.DSubTree;
import org.eclipse.jdt.core.dom.WhileStatement;

public class DOMWhileStatement implements Handler {

    final WhileStatement statement;

    public DOMWhileStatement(WhileStatement statement) {
        this.statement = statement;
    }

    @Override
    public DSubTree handle() {
        DSubTree tree = new DSubTree();

        DSubTree cond = new DOMExpression(statement.getExpression()).handle();
        DSubTree body = new DOMStatement(statement.getBody()).handle();

        boolean loop = cond.isValid();

        if (loop)
            tree.addNode(new DLoop(cond.getNodesAsCalls(), body.getNodes()));
        else {
            // only one of these will add nodes
            tree.addNodes(cond.getNodes());
            tree.addNodes(body.getNodes());
        }

        return tree;
    }
}