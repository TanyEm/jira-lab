package com.atlassian.plugins.lab.jiralab;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.templaterenderer.TemplateRenderer;
import java.util.ArrayList;
import java.util.Map;
import static com.google.common.base.Preconditions.*;
import com.google.common.collect.Maps;

public class LabServlet extends HttpServlet
{
	private final ActiveObjects ao;
	private final TemplateRenderer renderer;

	public LabServlet(TemplateRenderer renderer, ActiveObjects ao)
	{
	  this.renderer = renderer;
	  this.ao = checkNotNull(ao);
	}
	
	//GET
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		final ArrayList<LabAo> dataArray = new ArrayList<LabAo>();
		Map<String, Object> context = Maps.newHashMap();
//		renderer.render("lab.vm", response.getWriter());
		ao.executeInTransaction(new TransactionCallback<Void>()
		{
	@Override
    public Void doInTransaction()
    {
        for (LabAo labao : ao.find(LabAo.class)) // (2)
        {
        	dataArray.add(labao);
        }
        return null;
    }
		});
		context.put("Data", dataArray);
		renderer.render("lab.vm", context, response.getWriter());
	}

	//POST
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        final String stringData = req.getParameter("string");
        final String dateData = req.getParameter("date");
        ao.executeInTransaction(new TransactionCallback<LabAo>()
        {

     @Override
     public LabAo doInTransaction()
     {
    	 final LabAo labao = ao.create(LabAo.class);
    	 labao.setString(stringData); 
    	 labao.setDate(dateData);
    	 labao.save();
     return labao;
     }
        });

        res.sendRedirect(req.getContextPath() + "/plugins/servlet/jira/lab");
    }
}