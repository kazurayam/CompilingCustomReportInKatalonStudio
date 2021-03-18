package my

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

public class Memo {

	private TestSuiteContext testSuite
	private String executionProfile
	private String executedBrowser
	private List<TestCaseContext> testCases
	private Map<String, Object> executionProperties
	private Map<String, Object> globalVariables

	Memo() {
		this(null)
	}

	Memo(TestSuiteContext testSuiteContext) {
		this.testSuite = testSuiteContext
		this.testCases = new ArrayList<TestCaseContext>()
		this.globalVariables = GlobalVariableSupport.aquireGlobalVariablesAsMap()
	}

	void setExecutionProfile(String executionProfile) {
		this.executionProfile =  executionProfile
	}

	void setExecutedBrowser(String executedBrowser) {
		this.executedBrowser = executedBrowser
	}

	void addTestCaseContext(TestCaseContext testCaseContext) {
		testCases.add(testCaseContext)
	}

	void setExecutionProperties(Map map) {
		this.executionProperties = map
	}

	String toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create()
		String json = gson.toJson(this)
		return json
	}

	String toSortedJson() {
		String json = this.toJson()
		JsonElement je = new JsonParser().parse(json)
		JsonElement jeSorted = JsonUtil.sort(je)
		Gson gson = new GsonBuilder().setPrettyPrinting().create()
		return gson.toJson(jeSorted)
	}

	String printableMessages() {
		StringBuilder sb = new StringBuilder()
		testCases.forEach({ testCaseContext ->
			String message = testCaseContext.getMessage()
			if (message.length() > 0) {
				String[] lines = message.split("\\n")
				lines.each { String line ->
					sb.append(line.replaceAll("\\t", '  '))
					sb.append("\n")
				}
			}
		})
		return sb.toString()
	}
	
}
