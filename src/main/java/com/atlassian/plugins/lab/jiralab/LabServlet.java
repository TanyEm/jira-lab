package com.atlassian.plugins.lab.jiralab;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.crowd.embedded.api.Query;
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
		ao.executeInTransaction(new TransactionCallback<Void>()
		{
			@Override
		    public Void doInTransaction()
		    {
		        for (LabAo labao : ao.find(LabAo.class))
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
        
        if (stringData.isEmpty() || dateData.isEmpty()){
        	
        	res.getWriter().write("EMPTY");
        	
        	res.getWriter().close();
        } else {
        	
	    	final LabAo labao = ao.create(LabAo.class);
	        ao.executeInTransaction(new TransactionCallback<LabAo>()
	        {
			     @Override
			     public LabAo doInTransaction()
			     {
			    	 labao.setString(stringData); 
			    	 labao.setDate(dateData);
			    	 labao.save();
			    	 return labao;
			     }
	        });
	        
	        String newElement = new String();
	        
	        newElement = "<tr id=\"row-data-"+labao.getID()+"\"><td>"+labao.getString()+"</td><td>"+labao.getDate()+"</td>";
	        newElement += "<td><button id=\"delete-button\" onclick=\"delData("+labao.getID()+")\"> Delete </button></td></tr>";
	        
	        res.getWriter().write(newElement);
	        
	        res.getWriter().close();
        }
    }
	
	//DELETE
		@Override
		protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	    {
	        final int IdForDelete = Integer.parseInt(req.getParameter("id"));
	        
	        ao.executeInTransaction(new TransactionCallback<Void>()
	        		{
	        			@Override
	        		    public Void doInTransaction()
	        		    {
	        		        LabAo DelData = ao.get(LabAo.class, IdForDelete);
	        		        ao.delete(DelData);
	        		        return null;
	        		    }
	        		});
	    }

}