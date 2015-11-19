package ms5000.web.acusticid;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import ms5000.web.acousticid.result.Result;
import ms5000.web.acousticid.result.Results;
import ms5000.web.httputil.HTTPUtil;
import ms5000.web.httputil.Standard_Response;
import ms5000.web.httputil.WebService;
import com.google.gson.Gson;

public class AcoustID {
	private final static String PROPERTIES = "properties/http.properties";
	
	/**
	 * Chromaprint the file passed in
	 */
	public static ChromaPrint chromaprint(File file) throws IOException {
		String line;
		String chromaprint = null;
		String duration = null;
		
		// Setting the Properties
		Properties properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(PROPERTIES));
	    properties.load(stream);
	    stream.close();
	    
	    // Setting the Properties for the FpCalc - Process
		final ProcessBuilder processBuilder = new ProcessBuilder(properties.getProperty("fpcalc"), null);
		processBuilder.redirectErrorStream(true);
		processBuilder.command().set(1, file.getAbsolutePath());
		
		// Launching the Process
		final Process fpcalcProc = processBuilder.start();
		final BufferedReader br = new BufferedReader(new InputStreamReader(fpcalcProc.getInputStream()));
		
		// Reading the input-stream received by the process
		while ((line = br.readLine()) != null) {
			if (line.startsWith("FINGERPRINT=")) {
				chromaprint = line.substring("FINGERPRINT=".length());
			} else if (line.startsWith("DURATION=")) {
				duration = line.substring("DURATION=".length());
			}
		}
		
		// Returning new ChromaPrint
		return new ChromaPrint(chromaprint, duration);
	}

	/**
	 * get the highest rated result
	 */
	private static Result getBestResult(Results results) {
		if (results.getResults().size() > 0) {
			Result bestResult = results.getResults().get(0);
			double currentScore = bestResult.getScore();

			for (Result result : results.getResults()) {

				double score = result.getScore();

				if (score > currentScore) {
					bestResult = result;
					currentScore = score;
				}

			}
			return bestResult;
		} else {
			return null;
		}
	}

	/**
	 * get the Results object from JSON
	 */
	private static Results getResults(String json) {
		Gson gson = new Gson();
		Results results = gson.fromJson(json, Results.class);
		return results;
	}

	/**
	 * do a ChromaPrint lookup and result a musicbrainz id
	 */
	public static Result lookup(ChromaPrint chromaprint) throws IOException {
		String url = null;
		
		Properties properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(PROPERTIES));
	    properties.load(stream);
	    stream.close();
	      
		url = properties.getProperty("url_acoustid") + "?client="
				+ properties.getProperty("client_acoustid") + "&meta=recordings+compress"
				+ "&fingerprint=" + chromaprint.chromaprint + "&duration="
				+ chromaprint.duration;
		Standard_Response response = (Standard_Response) HTTPUtil.get(url,WebService.ACOUSTID);
		String json = response.getResponse();
		System.out.println(json);
		Results results = getResults(json);
		
		/*
		 * check status
		 */
		if (results.getStatus().compareTo("ok") == 0) {
			
			/*
			 * get the best match
			 */
			Result bestResult = getBestResult(results);
			
			if (bestResult != null) {
				/*
				 * return the id
				 */
				if (bestResult.getRecordings().size() > 0) {
					return bestResult;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	
}
