package com.engagepoint.controller.page;


import javax.enterprise.context.SessionScoped;
import org.primefaces.context.RequestContext;
import javax.inject.Named;
import com.engagepoint.controller.utils.PageNavigator;
import com.engagepoint.model.question.Question;
import com.engagepoint.model.question.rules.Rule;
import com.engagepoint.model.question.utils.QuestionAnswer;
import com.engagepoint.model.questionnaire.BasicBean;
import com.engagepoint.model.questionnaire.GroupBean;
import com.engagepoint.model.questionnaire.SectionBean;
import com.engagepoint.model.questionnaire.TemplateBean;

import java.io.Serializable;
import java.util.*;

@Named
@SessionScoped
public class RulesTestController extends RuleController implements Serializable { 

	private List<BasicBean> templateElementsList;
	private Map<BasicBean, List<BasicBean>> dependencies;
    private List<BasicBean> beansWithRules;
	private Set<BasicBean> notRenderedSet;
    private static final String QUESTION = "question";
    private static final String GROUP = "group";
    private static final String SECTION = "section";
	private TemplateBean currentTemplate;
    private BasicBean currentBean;
	
	public void resetRulerList(){
		prepareQuestionList();
		prepareDependencies();
        prepareRules();
		if(notRenderedSet!=null){
			notRenderedSet.clear();
        }
	}

    public Map<BasicBean, List<BasicBean>> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Map<BasicBean, List<BasicBean>> dependencies) {
		this.dependencies = dependencies;
	}

	public List<BasicBean> getTemplateElementsList() {
		return templateElementsList;
	}

	public void setTemplateElementsList(List<BasicBean> templateElementsList) {
		this.templateElementsList = templateElementsList;
	}

	
	public Set<BasicBean> getNotRenderedSet() {
		return notRenderedSet;
	}

	public void setNotRenderedSet(Set<BasicBean> notRenderedSet) {
		this.notRenderedSet = notRenderedSet;
	}

	/**
	 * build a list of all template elements from current template
	 */
	private void prepareQuestionList() {
		if (currentTemplate != null) {
			templateElementsList = new ArrayList<BasicBean>();
			for (SectionBean s : currentTemplate.getSectionsList()) {
				templateElementsList.add(s);
				for (GroupBean gr : s.getGroupsList()) {
					templateElementsList.add(gr);
					templateElementsList.addAll(gr.getQuestionsList());
				}
			}
		}
	}

	/**
	 * prepare a String presentation of Template element to display in table
	 * 
	 * @param bb
	 * @return
	 */
	public String displayTemplateElement(BasicBean bb) {
		String result = bb.getId();
		// TODO change to element name

		String s = bb.getType();
		if (SECTION.equals(s)) {
			return result;
		} else if (GROUP.equals(s)) {
			return "___" + result;
		} else if (QUESTION.equals(s)) {
			return "______" + result;
		} else {
			return "";
		}
	}

	/**
	 * build a map of dependencies which contains template element and a list of
	 * elements ids that are dependent on this question
	 */
	private void prepareDependencies() {
		dependencies = new HashMap<BasicBean, List<BasicBean>>();

		for (BasicBean bb : templateElementsList) {
			if ((bb.getRules() != null) && (!bb.getRules().isEmpty())) {
				for (Rule r : bb.getRules()) {
					BasicBean parent = getElementById(r.getId());
					if (dependencies.get(parent) == null) {
						dependencies.put(parent, new ArrayList<BasicBean>());
					}
					dependencies.get(parent).add(bb);
				}
			}

		}
	}

    private void prepareRules() {
        beansWithRules = new ArrayList<BasicBean>();
        for(BasicBean bean : templateElementsList){
            if(bean.getRules()!=null && ! bean.getRules().isEmpty())
                beansWithRules.add(bean);
        }
    }

	/**
	 * get an Template element object with specified ID
	 * 
	 * @param id
	 * @return
	 */
	private BasicBean getElementById(String id) {
		for (BasicBean bb : templateElementsList) {
			if (bb.getId().equals(id)) {
				return bb;
			}
		}
		return null;
	}

	/**
	 * Get page name and perform redirect.
	 * 
	 * @return page name
	 */
	public static String income() {
		return PageNavigator.RULES_TEST_PAGE;
	}

	/**
	 * TODO TEMP method for development phase, will
	 * 
	 * @param q
	 * @return
	 */

	public String getDependentByQuestion(BasicBean q) {
		List<BasicBean> result = dependencies.get(q);
		if (result == null) {
			return "";
		}

		String res = "";

		for (BasicBean bb : result){
			res += bb.getId() + " ";
        }
		return res;
	}

	private void setRedtoElement(BasicBean basicBean){
		if (QUESTION.equals(basicBean.getType())) {
			notRenderedSet.add(basicBean);
			setRedtoDependent(basicBean);
			// set Red to all dependent elements from this question
		} else if (GROUP.equals(basicBean.getType())) {
			setRedGroup(basicBean);
		} else if (SECTION.equals(basicBean.getType())) {
			setRedSection(basicBean);
		}
		
	}
	
	private void setRedtoDependent(BasicBean basicBean) {
		List<BasicBean> dependent = dependencies.get(basicBean);

		if (dependent != null) {
			for (int i = 0; i < dependent.size(); i++) {
			// TODO SHOULD BE TESTED
				setRedtoElement(dependent.get(i));
			}
		}
	}

	private void setRedSection(BasicBean bb) {
		notRenderedSet.add(bb);
		for (int i = templateElementsList.indexOf(bb) + 1; i < templateElementsList
				.size()
				&& (GROUP.equals(templateElementsList.get(i).getType()) || QUESTION.equals(templateElementsList.get(i).getType())); i++) {
			notRenderedSet.add(templateElementsList.get(i));
			setRedtoDependent(templateElementsList.get(i));
		}

	}

	private void setRedGroup(BasicBean bb) {
		notRenderedSet.add(bb);
		for (int i = templateElementsList.indexOf(bb) + 1; i < templateElementsList
				.size()
				&& QUESTION.equals(templateElementsList.get(i).getType()); i++) {
			notRenderedSet.add(templateElementsList.get(i));
			setRedtoDependent(templateElementsList.get(i));
		}

	}


	public TemplateBean getCurrentTemplate() {
		return currentTemplate;
	}


	public void setCurrentTemplate(TemplateBean currentTemplate) {
		this.currentTemplate = currentTemplate;
		//TODO change to invocation
		resetRulerList();
		
	}
	
	
	// TODO complete
	public void testRule() {
		notRenderedSet = new HashSet<BasicBean>();
		for (BasicBean bb : dependencies.get(getDependentQuestion())) {
			for (Rule r : bb.getRules()) {
                QuestionAnswer rAnswer = new QuestionAnswer();
                rAnswer.setAnswer(r);
                if(rAnswer.equals(answerForTests)) {
                	setRedtoElement(bb);
					setRedtoDependent(bb);
				}
			}
		}

	}

    public boolean getViewRuleButton(BasicBean q){
        if(beansWithRules.contains(q))
            return true;
        return false;
    }

    public void showRuleForQuestionButton(BasicBean bean){
        currentBean = bean;

        RequestContext.getCurrentInstance().execute("showRulesdialog.show()");
        RequestContext.getCurrentInstance().update("form1:rules");  //execute("rulesForm:rules.show()");
    }

    public List<Rule> getRulesForQuestion(){
        if(currentBean!=null)
            return currentBean.getRules();
        return null;
    }
	

    public void cleanDependencies(){
        currentTemplate = templateTreeController.getTemplateBean();
        resetRulerList();
        if(templateTreeController!=null && QUESTION.equals(templateTreeController.getSelectedType())){
            cleanFromQuestionDelete((BasicBean) templateTreeController.getSelectedNode().getData());
        }else
        if(templateTreeController!=null && GROUP.equals(templateTreeController.getSelectedType())){
            cleanFromGroupDelete((BasicBean) templateTreeController.getSelectedNode().getData());
        }else
        if(templateTreeController!=null && SECTION.equals(templateTreeController.getSelectedType())){
            cleanFromSectionDelete((BasicBean) templateTreeController.getSelectedNode().getData());
        }
    }

    private void cleanFromQuestionDelete(BasicBean question) {
        Map<BasicBean, List<BasicBean>> map = getDependencies();
        List<BasicBean> list = map.get(question);
        if(list!=null){
            for (BasicBean basicBean : list) {
                for (Iterator<Rule> iterator = basicBean.getRules().iterator();
                                               iterator.hasNext(); ) {
                    if(iterator.next().getId().equals(question.getId())){
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void cleanFromGroupDelete(BasicBean group){
        GroupBean temp = (GroupBean) group;
        for (Question question : temp.getQuestionsList()) {
            cleanFromQuestionDelete(question);
        }
    }

    private void cleanFromSectionDelete(BasicBean section){
        SectionBean temp = (SectionBean) section;
        for (GroupBean group : temp.getGroupsList()) {
            cleanFromQuestionDelete(group);
        }
    }

}
