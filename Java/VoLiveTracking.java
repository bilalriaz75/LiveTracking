package com.tcm.nestle.webcontroller.score;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tcm.framework.common.SysConstants;
import com.tcm.framework.utils.StringUtils;
import com.tcm.framework.webcontroller.BaseController;
import com.tcm.nestle.dao.VirtualViewDao;

@WebServlet(name = "VoLiveTracking", urlPatterns =
{ "/vo-live-tracking" })
public class VoLiveTracking extends BaseController
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
	//ss

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		try
		{
			showParameters(request);
			Gson gson = new Gson();
			int supervisorId = StringUtils.parseInt(request.getParameter("supervisorId"), -1);
			int surveyorId = StringUtils.parseInt(request.getParameter("surveyorId"), -1);
			String startDate = request.getParameter("startDate");
			String type=request.getParameter("type");
			logger.info(" VO Live Tracking request for Supervisor Id : " + supervisorId
					+ " Surveyor Id : " + surveyorId + " Date : " + startDate);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<Map<String, Object>> liveTrackingDataMap = null;
			if (SysConstants.getProjectName().equalsIgnoreCase(SysConstants.PROJECT_NAME_PMI_RM)) {
				liveTrackingDataMap = VirtualViewDao.getLiveTracker(
						startDate, surveyorId, type);
			}
			else {
				liveTrackingDataMap = VirtualViewDao.getLiveTracker(
						startDate, surveyorId, supervisorId);
			}
			
			List<Map<String, Object>> shopDataMap = VirtualViewDao.getLiveTrackerShops(startDate,
					surveyorId, supervisorId);
			dataMap.put("liveTrackingDataMap", liveTrackingDataMap);
			dataMap.put("shopDataMap", shopDataMap);
			PrintWriter out = response.getWriter();
			out.println(gson.toJson(dataMap));
			out.flush();
			out.close();

		}
		catch (Exception e)
		{
			logger.error(e, e);
		}

	}
}
