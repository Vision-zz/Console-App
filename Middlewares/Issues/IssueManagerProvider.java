package Middlewares.Issues;

public interface IssueManagerProvider {
	DevIssueManager getDevIssueManager();
	EngineerIssueManager getEngineerIssueManager();
	AdminIssueManager getAdminIssueManager();
}
