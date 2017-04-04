package com.viartemev.requestmapper;

import com.intellij.ide.util.gotoByName.CustomMatcherModel;
import com.intellij.ide.util.gotoByName.FilteringGotoByModel;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static com.viartemev.requestmapper.utils.CommonUtils.isSimilar;
import static com.viartemev.requestmapper.utils.CommonUtils.contains;

public class RequestMappingModel extends FilteringGotoByModel<FileType> implements DumbAware, CustomMatcherModel {

    public RequestMappingModel(@NotNull Project project) {
        super(project, new ChooseByNameContributor[]{new RequestMappingContributor()});
    }

    @Nullable
    @Override
    protected FileType filterValueFor(NavigationItem item) {
        return null;
    }

    @Override
    public String getPromptText() {
        return "Enter mapping url";
    }

    @Override
    public String getNotInMessage() {
        return "No matches found";
    }

    @Override
    public String getNotFoundMessage() {
        return "Mapping not found";
    }

    @Nullable
    @Override
    public String getCheckBoxName() {
        return null;
    }

    @Override
    public char getCheckBoxMnemonic() {
        return 0;
    }

    @Override
    public boolean loadInitialCheckBoxState() {
        return false;
    }

    @Override
    public void saveInitialCheckBoxState(boolean state) {

    }

    @NotNull
    @Override
    public String[] getSeparators() {
        return new String[0];
    }

    @Nullable
    @Override
    public String getFullName(Object element) {
        return getElementName(element);
    }

    @Override
    public boolean willOpenEditor() {
        return false;
    }

    @Override
    public boolean matches(@NotNull String popupItem, @NotNull String userPattern) {
        if ((!userPattern.contains(" ")) && (!userPattern.contains("/"))) {
            return popupItem.contains(userPattern);
        }

        String requiredPath = userPattern.substring(
                userPattern.indexOf('/'),
                userPattern.indexOf('?') == -1 ? userPattern.length() : userPattern.indexOf('?')
        );

        String[] itemList = Arrays.stream(popupItem.split("/"))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .toArray(String[]::new);

        String[] requiredPathList = Arrays.stream(requiredPath.split("/"))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .toArray(String[]::new);

        if ((requiredPathList.length > 0) && (itemList.length > 0) && (contains(requiredPathList, 0, itemList, 0))) {
            return true;
        }
        return isSimilar(requiredPathList, itemList);
    }

}
