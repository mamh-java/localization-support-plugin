/*
 * The MIT License
 *
 * Copyright (c) 2018 CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.jenkins.plugins.localization.support.localizer;

import hudson.ExtensionList;
import hudson.ExtensionListListener;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import io.jenkins.plugins.localization.support.LocalizationContributor;
import org.jvnet.localizer.ResourceBundleHolder;
import org.jvnet.localizer.ResourceProvider;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

/**
 * Utility class to configure the localizer library.
 */
@Restricted(NoExternalUse.class)
public class LocalizerManager extends ExtensionListListener {
    /**
     * Listen for changes to known {@link LocalizationContributor} implementations to invalidate the cache in
     * {@link ResourceBundleHolder}.
     */
    @Override
    public void onChange() {
        ResourceBundleHolder.clearCache();
    }

    @Initializer(after = InitMilestone.JOB_LOADED)
    public static void initialize() {
        ResourceProvider.setProvider(new ResourceProviderImpl());
        ResourceBundleHolder.clearCache();
        ExtensionList.lookup(LocalizationContributor.class).addListener(new LocalizerManager());
    }
}
