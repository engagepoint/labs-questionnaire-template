package com.engagepoint.controller.page;

import com.engagepoint.controller.utils.PageNavigator;
import com.engagepoint.model.questionnaire.TemplateBean;
import com.engagepoint.model.table.ListOfTemplatesDataModel;
import com.engagepoint.utils.XmlImportExport;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.io.*;
import java.util.*;


/**
 * Used for controlling index.xhtml
 */
@Named
@SessionScoped
public class ListController implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<TemplateBean> list;
    private List<TemplateBean> filteredList;
    private List<TemplateBean> selectedTemplates;
    private ListOfTemplatesDataModel templatesModel;
    private String filterValue = "";
    
    private static final Logger LOG = Logger.getLogger(ListController.class);

    public ListController() {
        list = new ArrayList<TemplateBean>();
    }

    @PostConstruct
    void init() {
        //searching path of XML file in glassfish
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String xmlPath = classLoader.getResource("Questionnaire.xml").getPath();
        //adding Templates from XML file
        addAllTemplates(XmlImportExport.importXmlTemplate(xmlPath));
        templatesModel = new ListOfTemplatesDataModel(list);
        selectedTemplates = new ArrayList<TemplateBean>();
    }

    public ListOfTemplatesDataModel getTemplatesModel() {
        return templatesModel;
    }

    public List<TemplateBean> getTemplates() {
        return list;
    }

    public void setTemplates(List<TemplateBean> list) {
        this.list = list;
    }

    public List<TemplateBean> getSelectedTemplates() {
        return selectedTemplates;
    }

    public void setSelectedTemplates(List<TemplateBean> selectedTemplates) {
        this.selectedTemplates = selectedTemplates;
    }

    public List<TemplateBean> getFilteredTemplates() {
        return filteredList;
    }

    public void setFilteredTemplates(List<TemplateBean> filteredList) {
        this.filteredList = filteredList;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    private boolean containsFilterValue(String name) {
        return name.toLowerCase().contains(filterValue.toLowerCase());
    }

    /**
     * Add template to list if it is not already there.
     *
     * @param template template to be added
     */
    public void addTemplateIfNotInList(TemplateBean template) {
        if (!list.contains(template)) {
            this.list.add(template);
        }
    }

    /**
     * Add template to lists and update lists in UI.
     *
     * @param template template to be added
     */
    public void addTemplateAndUpdateLists(TemplateBean template) {
        addTemplateIfNotInList(template);
        addTemplateToFilteredListIfNeed(template);
        templatesModel = new ListOfTemplatesDataModel(list);
        sort();
    }

    /**
     * Add template to filtered list
     * if filtered list is not empty
     * and if template is not already there
     * and if template satisfies current filter.
     *
     * @param template template to be added
     */
    public void addTemplateToFilteredListIfNeed(TemplateBean template) {
        if (filteredList != null && !filteredList.contains(template) && containsFilterValue(template.getTemplateName())) {
            //check if template with the same id exists
            //check if template satisfies current filter
            filteredList.add(template);
        }
    }

    /**
     * Remove template from filtered list
     * if it is not empty
     * and if template doesn't satisfy current filter
     * or if main list doesn't contain this template anymore (it was removed).
     *
     * @param template template to be added
     */
    public void removeTemplateFromFilteredListIfNeed(TemplateBean template) {
        if (filteredList != null) {
            if (!containsFilterValue(template.getTemplateName())) {
                filteredList.remove(template);
            } else {
                if (!list.contains(template)) {
                    filteredList.remove(template);
                }
            }
        }
    }

    /**
     * Add templates from list to both lists and update UI.
     *
     * @param templateBeanList template list to be added
     */
    public void addAllTemplates(List<TemplateBean> templateBeanList) {
        for (TemplateBean templateBean : templateBeanList) {
            addTemplateIfNotInList(templateBean);
            addTemplateToFilteredListIfNeed(templateBean);
        }
        templatesModel = new ListOfTemplatesDataModel(list);
        sort();
    }

    /**
     * Sort both lists of templates.
     */
    public void sort() {
        Collections.sort(list);
        if (filteredList != null) {
            Collections.sort(filteredList);
        }
    }

    /**
     * Delete template from lists.
     *
     * @param template template to be deleted
     */
    public void deleteTemplate(TemplateBean template) {
        list.remove(template);
        removeTemplateFromFilteredListIfNeed(template);
    }

    /**
     * Clone template.
     *
     * @param template template to be clonned
     * @throws CloneNotSupportedException
     */
    public void clone(TemplateBean template) throws CloneNotSupportedException {
        TemplateBean newTemplate = (TemplateBean) template.clone();
        newTemplate.setTemplateName(newTemplate.getTemplateName() + " - clone");
        newTemplate.setFormId("f"+newTemplate.getId());
        addTemplateAndUpdateLists(newTemplate);
    }

    /**
     * Create temporary XML file before downloading
     */
    public void onExportXML() {
        Collections.sort(selectedTemplates);
        try {
            File tmpFile = FileDownloadController.createTempXml(selectedTemplates);
            FileDownloadController.setPathToTempFile(tmpFile.getPath());
        } catch (IOException e) {
            LOG.error("Export XML List Exception", e);
        }
    }

    /**
     * Get message from messages.properties.
     *
     * @param propertyKey key of messages.properties
     */
    public static String getMessageProperty(String propertyKey) {
        return getResourceBundleString("msgs", propertyKey);
    }

    /**
     * Get value from resource bundle.
     *
     * @param resourceBundleName name of resource bundle
     * @param resourceBundleKey  key of resource bundle
     */
    public static String getResourceBundleString(
            String resourceBundleName,
            String resourceBundleKey) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle bundle =
                facesContext.getApplication().getResourceBundle(
                        facesContext, resourceBundleName);
        return bundle.getString(resourceBundleKey);
    }

    /**
     * Get page name and perform redirect.
     *
     * @return page name
     */
    public static String income() {
        return PageNavigator.INDEX_PAGE;
    }
}
