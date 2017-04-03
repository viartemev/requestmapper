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
        System.out.println("Path: " + requiredPath);

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

    private static boolean isSimilar(String[] patternList, String[] itemList) {
        if (itemList.length < patternList.length) {
            return false;
        }
        for (int i = 0; (i < patternList.length) && (i < itemList.length); i++) {
            String pattern = patternList[i];
            String item = itemList[i];
            if ((!item.startsWith("{")) || (!item.endsWith("}"))) {
                if (!pattern.equals(item)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean contains(String[] patternList, int patternIdx, String[] itemList, int itemIdx) {
        if ((itemIdx >= itemList.length) || (patternIdx >= patternList.length)) {
            return false;
        }
        String currentItem = itemList[itemIdx];
        if (patternIdx < patternList.length) {
            int restPattern = patternList.length - patternIdx;
            int restItem = itemList.length - itemIdx;
            if (restItem < restPattern) {
                return false;
            }
            String currentPattern = patternList[patternIdx];
            if (currentItem.contains(currentPattern)) {
                if (patternIdx == patternList.length - 1) {
                    return true;
                }
                return contains(patternList, patternIdx + 1, itemList, itemIdx + 1);
            }
            return contains(patternList, patternIdx, itemList, itemIdx + 1);
        }

        return false;
    }

}
