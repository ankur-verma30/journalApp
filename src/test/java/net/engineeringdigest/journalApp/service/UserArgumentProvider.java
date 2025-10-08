package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(Arguments.of(User.builder().userName("Ram").password("ram").build()), Arguments.of(User.builder().userName("Shyam").password("shyam").build()), Arguments.of(User.builder().userName("Ankur").password("ankur").build()), Arguments.of(User.builder().userName("Isha").password("isha").build()));
    }
}
