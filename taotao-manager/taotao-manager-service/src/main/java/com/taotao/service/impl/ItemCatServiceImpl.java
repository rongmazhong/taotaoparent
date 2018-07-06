package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈商品类目实现〉
 *
 * @author mazhongrong@smeyun.com
 * @date 2018/7/6
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    /**
     * getItemCatList 根据父节点id查询子节点列表
     * @param parentId 节点id
     * @return list
     * @author MZRong
     * @date 2018/7/6 15:19
     */
    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {

        TbItemCatExample example = new TbItemCatExample();
        // 设置查询条件
        Criteria criteria =  example.createCriteria();
        // 设置parentid
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        //转换成EasyUITreeNode列表
        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            // 若节点下有子节点"closed",没有"open"
            node.setState(tbItemCat.getIsParent() ? "closed" : "open");
            resultList.add(node);
        }
        return resultList;

    }
}
