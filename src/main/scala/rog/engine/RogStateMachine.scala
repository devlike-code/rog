package rog.engine

trait RogStateMachineNode {
    def enter() {}
    def update() {}
    def render() {}
    def exit() {}
}

trait RogStateMachineDriver[StateFamily <: RogStateMachineNode, Transition] { 
    def transitions: PartialFunction[(StateFamily, Transition), StateFamily]
    
    var currentState: StateFamily
    
    def trigger(transition: Transition) = {
        if (transitions.isDefinedAt((currentState, transition))) {
            currentState.exit();
            currentState = transitions.apply((currentState, transition));
            currentState.enter();
        }        
    }

    def update() = {
        currentState.update()
    }
    
    def render() =
        currentState.render()
}
