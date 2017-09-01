package me.nichijou.quartz;

import freemarker.template.TemplateException;
import me.nichijou.service.FetchService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * Created by nichijou on 8/31/17.
 */
public class FetchJob implements Job{
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
		try {
			applicationContext.getBean(FetchService.class).refreshArticles();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
