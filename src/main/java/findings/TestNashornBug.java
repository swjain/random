package findings;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;

public class TestNashornBug {
	
    public static void main(String[] args) throws ScriptException, ParseException {
    	
    	String script = 
    			"function run() {"
    			+ "	print('$ ' + (item.foo ? 'item.foo is ' + item.foo : 'item.foo either is null or undefined'));\n"
    			+ "	print('$ Type of item.foo is ' + (typeof item.foo));"
    			+ "	!(typeof item.foo === 'undefined') ? print('$ pass') : print('$ fail')"
    			+ "}"
    			+ "run();";
    	
    	Map<String, Object> scriptArgs = new HashMap<>();
    	Object obj = new JSONParser().parse("{ \"foo\": \"bar\", \"hash\": null}");
    	scriptArgs.put("item", obj);
    	
    	// things work absolutely fine here
    	runSnippetThrouhRhino(script, scriptArgs);
    	runSnippetThroughNashorn(script, scriptArgs);
    	
    	// nashorn fails to handle java null, results in inconsistent behavior
    	runSnippetThrouhRhino(script, scriptArgs);
    	runSnippetThroughNashorn(script, scriptArgs);
    	
    }

	private static void runSnippetThrouhRhino(String snippet, Map<String, Object> scriptArgs) throws ScriptException {
		
		System.out.println("\nEvaluating with Rhino:");
		Context cx = Context.enter();
		
        Global global = new Global(cx);
        for(Entry<String, Object> entry: scriptArgs.entrySet()) {
        	global.defineProperty(entry.getKey(), Context.javaToJS(entry.getValue(), global), 0);
        }
        cx.evaluateString(global, snippet, "jsSnippet", 1, null);
        
	}
	
	private static void runSnippetThroughNashorn(String snippet, Map<String, Object> scriptArgs) throws ScriptException {
	
		System.out.println("\nEvaluating with Nashorn:");
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(snippet, new SimpleBindings(scriptArgs));
        
	}

}

/*boolean
object
object
boolean
object
undefined
object
object

true
{name=Bob}
null
true
[object Object]
undefined
{name=Bob}
null


boolean
object
undefined
object
object


var item = new org.springframework.boot.json.JacksonJsonParser().parseMap('{"name": "Bob", "hash": null}');
java.lang.System.out.println(typeof item.hash)

		java.lang.System.out.print(val ? val : 'item is null or undefined');
		java.lang.System.out.println('; type is: '+ typeof val + '; ');
		
		java.lang.System.out.println('isBlock: typeof options.fn: ' + typeof options.fn );
		options.fn though is function in Rhino, returns object in nashorn
		
================================================================


true; type is: boolean; !!val is:true
[object Object]; type is: object; !!val is:true
item is null or undefined; type is: undefined; !!val is:false
com.github.jknack.handlebars.internal.js.RhinoHandlebars$OptionsJs@33065d67; type is: object; !!val is:true
[object Object]; type is: object; !!val is:true

true; type is: boolean; !!val is:true
{name=Bob}; type is: object; !!val is:true
item is null or undefined; type is: object; !!val is:false
com.github.jknack.handlebars.Options@5149f008; type is: object; !!val is:true
{}; type is: object; !!val is:true

================================================================

var util = {

    isNumber: function (val) {
        return (typeof val) === 'number';
    },

    isObject: function (val) {
        return !!val && (typeof val) === 'object';
    },

    isOptions: function (val) {
        return util.isObject(val) && util.isObject(val.hash);
    },

    isBlock: function (options) {
        return util.isOptions(options) && options.fn && options.inverse;
    },

    value: function (val, context, options) {
        if (util.isOptions(val)) {
            return util.value(null, val, options);
        }
        if (util.isOptions(context)) {
            return util.value(val, {}, context);
        }
        if (util.isBlock(options)) {
			java.lang.System.out.println('here');
            return !!val ? options.fn(context) : options.inverse(context);
        }
        return val;
    },

    contains: function (val, obj, start) {
        if (val == null || obj == null || !util.isNumber(val.length)) {
            return false;
        }
        return val.indexOf(obj, start) !== -1;
    },

    has: function (val, obj) {
        if (val == null || obj == null || !util.isNumber(val.length)) {
            return false;
        }
        return val.includes(obj);
    },

    isEven: function isEven(n) {
        return n % 2 == 0;
    }

};
*/
