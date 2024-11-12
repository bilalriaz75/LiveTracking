package com.tcm.nestle.webcontroller.score;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tcm.framework.common.SysConstants;
import com.tcm.framework.utils.StringUtils;
import com.tcm.framework.webcontroller.BaseController;
import com.tcm.nestle.dao.VirtualViewDao;

@WebServlet(name = "VoTracking", urlPatterns =
{ "/vo-tracking" })
public class VoTracking extends BaseController
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030488393911542575L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		try
		{
			showParameters(request);
			String responseJson = "";
			Gson gson = new Gson();
			int supervisorId = StringUtils.parseInt(request.getParameter("supervisorId"), -1);
			int surveyorId = StringUtils.parseInt(request.getParameter("surveyorId"), -1);
			String startDate = request.getParameter("startDate");
			logger.info(" VO Tracking request for Supervisor Id : " + supervisorId
					+ " Surveyor Id : " + surveyorId + " Date : " + startDate);
			if (SysConstants.getProjectName()
					.equalsIgnoreCase(SysConstants.PROJECT_NAME_COKE_AUDIT))
			{
				responseJson = gson.toJson(VirtualViewDao.getMonthWiseEvaluationVisitShops(
						startDate, surveyorId, supervisorId));
			}
			else
			{
				responseJson = gson.toJson(VirtualViewDao.getEvaluationVisitShops(startDate,
						surveyorId, supervisorId));
			}
			PrintWriter out = response.getWriter();
			out.println(responseJson);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			logger.error(e, e);
		}

	}

}
