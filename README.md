Hello, 

Thank you for your interest in GitRepoSearchEngine.
Real product demo link: http://ec2-34-238-49-81.compute-1.amazonaws.com:8080/gitRepoSearchEngine/index.html

Introduction:
    This is a web based application that allows you to search repositories from GitHub.
This application mainly utilize GitHub's search API. Read more on https://developer.github.com/v3/search/

Simple Search Filter:
    Currently, the search filter includes "users", "repositories", "numStars".
- users search filter: search all repositories with given user name in the input box
- repositories search filter: search all repositories with given keyword in the input box
- numStars search filter: search repositories base on number of stars a repository has. By checking "Use Range" box when search Criteria is pointing to "numStars"

    Repository Per Page indicates how many repositores will show from the search results. 

Advanced Search Filter:
    Currently only available to "repositories" and "numStars" searching criteria. It allows you filters the result to have the languages you desire to search. 

Result:
    - Paginations will show above the result table, and number in red indicates the page you are on.
    - When you click on the repository URL, it will ask you if you would like to open the link in another tab, selecting "OK" will open a new tab with the link.

Common/Questions please reach out to ayoko001@ucr.edu

Thanks,
-Akiyo
