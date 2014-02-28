As a template author I want to add “Date"
questions to question groups so that the
template meets my needs.
Scenario:
Narrative:
User can add questions on the
template creation/editing form.
When the user opens the default page
When clicks on element with id/name/className 'form1:AddTemplate'
Then should open page with 'Template Editor' title
When the user fills 'formTemplate:name' field with 'LQE-16 test'
When clicks on element with id/name/className 'formTemplate:form:addSection'
Then verify that in table 'formTemplate:form:treeMultiple' is displayed '1' elements per page
When in tree 'formTemplate:form:treeMultiple' user chooses node with 'Page 1'
Then wait until all animations on page completed
When clicks on element with id/name/className 'formTemplate:form:btnDisplayEdit'
Then wait until all animations on page completed
When in tree 'formTemplate:form:treeMultiple' user opens node with 'Page 1'
Then verify that in table 'formTemplate:form:treeMultiple' is displayed '2' elements per page
When in tree 'formTemplate:form:treeMultiple' user chooses node with 'GROUP_1'
When clicks on element with id/name/className 'formTemplate:form:btnDisplayEdit'
Then wait until all animations on page completed
Scenario:
Narrative:
User can select "Date" in the Question
Type drop-down list.

When clicks on element with id/name/className 'form:selectOneMenu_label'
Then wait until all animations on page completed
When choose 'DATE' from drop-down
Then wait until all animations on page completed
Scenario:
Narrative:
User can enter some text and default date answer for the question
in the Question Text field

When the user fills 'questionForm:form2:date:qtext' field with 'Test text'
Then wait until all animations on page completed
When the user fills 'questionForm:form2:date:helpText' field with 'Test text'
When the user fills 'questionForm:form2:calendar_input' field with '02/11/2014'

Scenario:
Narrative:
User can save the
template with added
questions.
When the user clicks on element with id/name/className 'buttons:savequestion'
Then wait until all animations on page completed
When in tree 'formTemplate:form:treeMultiple' user opens node with 'Page 1'
When in tree 'formTemplate:form:treeMultiple' user opens node with 'GROUP_1'
Then wait until all animations on page completed