package org.lf.community.service;

import org.apache.ibatis.session.RowBounds;
import org.lf.community.dto.NotificationDTO;
import org.lf.community.dto.PaginationDTO;
import org.lf.community.enums.NotificationEnum;
import org.lf.community.enums.NotificationStatusEnum;
import org.lf.community.exception.CustomizeCode;
import org.lf.community.exception.CustomizeException;
import org.lf.community.mapper.NotificationMapper;
import org.lf.community.model.Notification;
import org.lf.community.model.NotificationExample;
import org.lf.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if(page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(page, size, totalCount);
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notifications.size() == 0) {
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification.getReceiver() == null) {
            throw new CustomizeException(CustomizeCode.READ_NOTIFICATION_FAIL);
        }
        if(!Objects.equals(notification.getReceiver(),user.getId())) {
            throw new CustomizeException(CustomizeCode.NOTIFICATION_NOT_FOUND);
        }
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        notification.setStatus(1);
        notificationMapper.updateByPrimaryKey(notification);
        return notificationDTO;


    }
}
