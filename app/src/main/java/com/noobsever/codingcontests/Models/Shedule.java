package com.noobsever.codingcontests.Models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shedule implements Serializable
{

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("objects")
    @Expose
    private List<Object> objects = null;
    private final static long serialVersionUID = -6287322069637783544L;


    public Shedule() {
    }

    public Shedule(Meta meta, List<Object> objects) {
        super();
        this.meta = meta;
        this.objects = objects;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }


    //--------------------------Resorce------------------------------

    public class Resource implements Serializable
    {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        private final static long serialVersionUID = -3652127644136368607L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Resource() {
        }

        /**
         *
         * @param id
         * @param name
         */
        public Resource(Integer id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

//--------------------------------End_Resorse---------------------OBJESTs---------------------


    public class Object implements Serializable
    {

        @SerializedName("duration")
        @Expose
        private Integer duration;
        @SerializedName("end")
        @Expose
        private String end;
        @SerializedName("event")
        @Expose
        private String event;
        @SerializedName("href")
        @Expose
        private String href;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("resource")
        @Expose
        private Resource resource;
        @SerializedName("start")
        @Expose
        private String start;
        private final static long serialVersionUID = -8753631689532026035L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Object() {
        }

        /**
         *
         * @param id
         * @param duration
         * @param start
         * @param event
         * @param resource
         * @param href
         * @param end
         */
        public Object(Integer duration, String end, String event, String href, Integer id, Resource resource, String start) {
            super();
            this.duration = duration;
            this.end = end;
            this.event = event;
            this.href = href;
            this.id = id;
            this.resource = resource;
            this.start = start;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Resource getResource() {
            return resource;
        }

        public void setResource(Resource resource) {
            this.resource = resource;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

    }


    //----------------------------ENd_Objects------------------------------META--------------------------------



    public class Meta implements Serializable
    {

        @SerializedName("limit")
        @Expose
        private Integer limit;
        @SerializedName("next")
        @Expose
        private java.lang.Object next;
        @SerializedName("offset")
        @Expose
        private Integer offset;
        @SerializedName("previous")
        @Expose
        private java.lang.Object previous;
        @SerializedName("total_count")
        @Expose
        private Integer totalCount;
        private final static long serialVersionUID = -814445154582748336L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Meta() {
        }

        /**
         *
         * @param limit
         * @param previous
         * @param totalCount
         * @param next
         * @param offset
         */
        public Meta(Integer limit, java.lang.Object next, Integer offset, java.lang.Object previous, Integer totalCount) {
            super();
            this.limit = limit;
            this.next = next;
            this.offset = offset;
            this.previous = previous;
            this.totalCount = totalCount;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public java.lang.Object getNext() {
            return next;
        }

        public void setNext(java.lang.Object next) {
            this.next = next;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public java.lang.Object getPrevious() {
            return previous;
        }

        public void setPrevious(java.lang.Object previous) {
            this.previous = previous;
        }

        public Integer getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

    }


    //-------------------------------------------End_Meta------------------------------------------------------




}