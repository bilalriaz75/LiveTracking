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
import com.tcm.nestle.bo.AdminProfile;
import com.tcm.nestle.dao.UserProfileDao;
import com.tcm.nestle.dao.VirtualViewDao;

@WebServlet(name = "LiveTracking", urlPatterns =
{ "/live-tracking" })
public class LiveTracking extends BaseController
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
			AdminProfile user = UserProfileDao.findUserById(StringUtils.parseInt(
					request.getParameter("userId"), -1));
			int supervisorId = StringUtils.parseInt(request.getParameter("supervisorId"), -1);
			int surveyorId = StringUtils.parseInt(request.getParameter("surveyorId"), -1);
			String startDate = request.getParameter("startDate");
			int zoneId = StringUtils.parseInt(request.getParameter("zoneId"), -1);
			int regionId = StringUtils.parseInt(request.getParameter("regionId"), -1);
			logger.info("Live Tracking request for Supervisor Id : " + supervisorId
					+ " Surveyor Id : " + surveyorId + " Date : " + startDate);

			responseJson = gson.toJson(VirtualViewDao.getLiveTrackingVisitShops(startDate,
					surveyorId, supervisorId, zoneId, regionId, StringUtils.parseInt(
							SysConstants.getEvaluatorRole(), -1) == user.getTypeID() ? user.getUser_id()
							: -1));
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
