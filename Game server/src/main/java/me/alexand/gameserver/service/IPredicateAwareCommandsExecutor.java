package me.alexand.gameserver.service;

public interface IPredicateAwareCommandsExecutor extends ICommandsExecutor {

    void updatePredicate(IPredicate predicate);

}
