package com.poly.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.poly.dao.AbstractDao;
import com.poly.dao.StatsDao;
import com.poly.dto.VideoLikedInfo;
import com.poly.entity.User;

public class StatsDaoImpl extends AbstractDao<Object[]> implements StatsDao {

	@Override
	public List<VideoLikedInfo> findVideoLikedInfo() {
		String sql = "select\r\n"
				+ "	v.id , v.title , v.href , sum(CAST(h.isLiked as int)) as totalLike\r\n"
				+ "from \r\n"
				+ "	video v left join history h on v.id = h.videoId\r\n"
				+ "where\r\n"
				+ "	v.isActive = 1\r\n"
				+ "group by \r\n"
				+ "	v.id , v.title , v.href\r\n"
				+ "order by \r\n"
				+ "	sum(CAST(h.isLiked as int)) desc";
		List<Object[]> objects = super.findManyByNativeQuery(sql);
		
		List<VideoLikedInfo> result  = new ArrayList<>();
		objects.forEach(object -> {
			VideoLikedInfo videoLikeInfo = setDataVideoLikedInfo(object);
			
			result.add(videoLikeInfo);
		});
		return result;
	}
	
	
	private VideoLikedInfo setDataVideoLikedInfo(Object[] object) {
		VideoLikedInfo videoLikeInfo = new VideoLikedInfo();
		videoLikeInfo.setVideoId((Integer) object[0]);
		videoLikeInfo.setTitle((String) object[1]);
		videoLikeInfo.setHref((String) object[2]);
		videoLikeInfo.setTotalLike(object[3] == null ? 0 : (Integer)object[3]);
		return videoLikeInfo;
	}


}
