As a template author I want to search for questionnaire templates so that I
can quickly find theneeded ones.
Scenario:
Narrative:
User can enter some alphanumeric text for
searching for templates and full-text search is supported and only matching results are displayed.
When the user opens the default page
Then wait until all animations on page completed
When clicks on element with id/name/className 'form1:AddTemplate'
Then wait until all animations on page completed
When the user fills 'formTemplate:name' field with 'blahblah'
When the user clicks on element with id/name/className 'formTemplate:save'
Then wait until all animations on page completed
When the user fills 'form1:table:globalFilter' field with 'blahblah'
Then verify that in table 'form1:table' is displayed less than or equal '1' elements per page
When the user fills 'form1:table:globalFilter' field with ''
Then wait until all animations on page completed

Scenario:
Narrative:
Partial search (by part of the word) is
supported, search run on the templates names and search results can be cleared.
When the user fills 'form1:table:globalFilter' field with 'ah'
Then wait until all animations on page completed
Then verify that in table 'form1:table' is displayed less than or equal '1' elements per page
When the user fills 'form1:table:globalFilter' field with ''