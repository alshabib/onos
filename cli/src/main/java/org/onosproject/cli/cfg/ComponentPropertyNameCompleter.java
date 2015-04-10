/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.cli.cfg;

import java.util.List;
import java.util.SortedSet;

import org.apache.felix.service.command.CommandSession;
import org.apache.karaf.shell.console.CommandSessionHolder;
import org.apache.karaf.shell.console.Completer;
import org.apache.karaf.shell.console.completer.ArgumentCompleter;
import org.apache.karaf.shell.console.completer.StringsCompleter;
import org.onosproject.cfg.ComponentConfigService;
import org.onosproject.cli.AbstractShellCommand;

/**
 * Component property name completer.
 */
public class ComponentPropertyNameCompleter implements Completer {
    @Override
    public int complete(String buffer, int cursor, List<String> candidates) {
        // Delegate string completer
        StringsCompleter delegate = new StringsCompleter();

        CommandSession session = CommandSessionHolder.getSession();
        ArgumentCompleter.ArgumentList list =
                (ArgumentCompleter.ArgumentList) session.get(
                        ArgumentCompleter.ARGUMENTS_LIST);

        // Component name is the previous argument.
        String componentName = list.getArguments()[list.getCursorArgumentIndex() - 1];
        ComponentConfigService service =
                AbstractShellCommand.get(ComponentConfigService.class);

        SortedSet<String> strings = delegate.getStrings();
        service.getProperties(componentName)
                .forEach(property -> strings.add(property.name()));

        // Now let the completer do the work for figuring out what to offer.
        return delegate.complete(buffer, cursor, candidates);
    }

}