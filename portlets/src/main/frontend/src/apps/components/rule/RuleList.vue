<!--
This file is part of the Meeds project (https://meeds.io/).
Copyright (C) 2020 Meeds Association
contact@meeds.io
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.
You should have received a copy of the GNU Lesser General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
-->
<template>
  <b-container fluid>
    <b-row>
      <b-col sm="12">
        <div
          v-if="isdeleted"
          class="alert alert-success"
          v-on:="closeAlert()">
          <button
            aria-label="Close"
            class="close"
            data-dismiss="alert"
            style="line-height: 27px; margin-right: 5px;"
            type="button">
          </button>
          <i class="uiIconSuccess"></i>
          {{ this.$t('exoplatform.gamification.successdelete') }}
        </div>


        <div class="uiSearchForm uiSearchInput searchWithIcon">
          <input
            v-model="search"
            :placeholder="this.$t('exoplatform.gamification.gamificationinformation.domain.search')"
            name="keyword"
            type="text"
            value="">
          <a
            class="advancedSearch"
            data-placement="bottom"
            rel="tooltip"
            title="">
            <i class="uiIconSearch uiIconLightGray"></i>
          </a>
        </div>

        <div class="filter-bar">
          <select v-model="enabledFilter" class="mb-4">
            <option :value="null">{{ $t(`exoplatform.gamification.all`,"All") }}</option>
            <option :value="true">{{ $t(`exoplatform.gamification.enabled`,"Enabled") }}</option>
            <option :value="false">{{ $t(`exoplatform.gamification.disabled`,"Disabled") }}</option>
          </select>
        </div>

        <div
          id="collapseTwo"
          :class="isShown ? '' : 'out'"
          aria-labelledby="headingOne"
          class="collapse show"
          data-parent="#accordionExample"
          style="height: 0px; transition: inherit;">
          <div class="card-body">
            <div
              id="myForm"
              class="UIPopupWindow uiPopup UIDragObject NormalStyle"
              style="width: 760px; z-index:1000000; position: relative; left: auto; margin: 0 20px; z-index: 1; max-width: 100%;margin: 0 auto;height: 100%;">
              <div class="popupHeader ClearFix">
                <a class="uiIconClose pull-right" @click.prevent="collapseButtonn(rule)"></a>

                <span class="PopupTitle popupTitle">{{ this.$t('exoplatform.gamification.gamificationinformation.rule.popupedit') }}</span>
              </div>
              <div class="PopupContent popupContent">
                <form id="titleInputGroup">
                  <label class="col-form-label pt-0">{{ $t(`exoplatform.gamification.gamificationinformation.Event`)
                  }}:</label>


                  <select
                    v-model="editedrule.event"
                    class="mb-4 select-event"
                    required>
                    <option
                      disabled
                      selected
                      value="null">
                      {{ $t(`exoplatform.gamification.selectevent`) }}
                    </option>
                    <option v-for="option in events" :value="option">
                      {{
                        $t(`exoplatform.gamification.gamificationinformation.rule.title.${option}`,option)
                      }}
                    </option>
                  </select>
                </form>

                <form id="descriptionInputGroup">
                  <label class="col-form-label pt-0">{{ $t(`exoplatform.gamification.gamificationinformation.domain.Description`)
                  }}:</label>
                  <textarea
                    id="ruleDescription"
                    v-model="editedrule.description"
                    :max-rows="6"
                    :rows="3"
                    placeholder="Enter description">
                            </textarea>
                </form>
                <b-form id="scoreInputGroup">
                  <label
                    id="scoreInputGroup"
                    class="col-form-label pt-0"
                    for="scoreInput">{{ $t(`exoplatform.gamification.score`)
                    }}:</label>
                  <input
                    id="scoreDescription"
                    v-model="editedrule.score"
                    placeholder="Enter rule's score"
                    required
                    type="number">

                  <div
                    v-if="formErrors.score"
                    class="alert alert-danger require-msg"
                    :show="dismissCountDown"
                    dismissible
                    variant="danger"
                    @dismiss-count-down="countDownChanged"
                    @dismissed="dismissCountdown=0">
                    Rule score is required please enter a score
                  </div>
                </b-form>
                <form class="switchEnabled">
                  <label class="col-form-label pt-0" max-rows="6">{{ $t(`exoplatform.gamification.enabled`)
                  }}:</label>
                  <label class="switch">
                    <input
                      v-model="editedrule.enabled"
                      :disabled="editedrule.domainDTO==null||!editedrule.domainDTO.enabled"
                      type="checkbox">
                    <div class="slider round"><span class="absolute-yes">{{ $t(`exoplatform.gamification.YES`,"YES") }}</span></div>
                    <span class="absolute-no">{{ $t(`exoplatform.gamification.NO`) }}</span>
                  </label>
                  <div v-if="editedrule.domainDTO==null||!editedrule.domainDTO.enabled" class="error"> *{{ $t(`exoplatform.gamification.disabledDomainForRules`,"This rule cannot be enabled as long as the related domain is disabled") }}.</div>
                </form>
                <form id="domainSelectboxGroup">
                  <label class="col-form-label pt-0" style="margin-left: 20%">{{ $t(`exoplatform.gamification.gamificationinformation.Domain`)
                  }}:</label>
                  <select v-model="editedrule.domainDTO" required>
                    <option disabled value="null">
                      {{ $t(`exoplatform.gamification.selectdM`) }}
                    </option>
                    <option v-for="option in domains" :value="option">
                      {{
                        $t(`exoplatform.gamification.gamificationinformation.domain.${option.title}`,option.title)
                      }}
                    </option>
                  </select>
                </form>

                <div class="row">
                  <b-col>
                    <button
                      class="btn secondary pull-right"
                      type="submit"
                      @click.prevent="collapseButtonn(editedrule), onCancel()">
                      {{
                        this.$t('exoplatform.gamification.gamificationinformation.domain.cancel')
                      }}
                    </button>
                    <button
                      class="btn-primary pull-right"
                      type="submit"
                      :disabled="isBottonDisabled"
                      style="margin-left: 500px;"
                      @click.prevent="onSave(editedrule),collapseButtonn(editedrule)">
                      {{
                        this.$t('exoplatform.gamification.gamificationinformation.domain.confirm')
                      }}
                    </button>
                  </b-col>
                </div>
              </div>
            </div>
          </div>
        </div>


        <div
          id="collapseOne"
          :class="isShowndeleted ? '' : 'out'"
          aria-labelledby="headingOne"
          class="collapse show"
          data-parent="#accordionExample"
          style=" transition: inherit;">
          <div class="card-body">
            <div
              id="myForm2"
              class="UIPopupWindow uiPopup UIDragObject NormalStyle"
              style="width: 760px; z-index:1000000; position: relative; left: auto; margin: 0 20px; z-index: 1; max-width: 100%;margin: 0 auto;height: 100%;">
              <div class="popupHeader ClearFix">
                <a class="uiIconClose pull-right" @click.prevent="collapseConfirm(rule)"></a>

                <span class="PopupTitle popupTitle">{{ this.$t('exoplatform.gamification.rule.popupdelete') }}</span>
              </div>
              <div class="PopupContent popupContent">
                <div class="media">
                  <div class="pull-left">
                    <i class="uiIconColorQuestion"></i>
                  </div>
                  <div class="media-body">
                    <p class="msg">
                      {{ this.$t('exoplatform.gamification.areyousure.deleterule')
                      }}
                    </p>
                  </div>
                </div>
                <div class="uiAction uiActionBorder">
                  <b-col>
                    <button
                      class="btn cancel pull-right"
                      type="submit"
                      @click.prevent="collapseConfirm(rule), onCancel()">
                      {{
                        this.$t('exoplatform.gamification.gamificationinformation.domain.cancel')
                      }}
                    </button>

                    <button
                      class="btn-primary pull-right"
                      type="submit"
                      style="margin-left: 0 !important; margin-right: 9px;"
                      @click.prevent="onRemove(rule.id,rule.title),collapseConfirm(rule)">
                      {{
                        this.$t('exoplatform.gamification.gamificationinformation.domain.confirm')
                      }}
                    </button>
                  </b-col>
                </div>
              </div>
            </div>
          </div>
        </div>


        <table
          striped
          hover
          class="uiGrid table table-hover rule-table">
          <thead>
            <tr>
              <th class="rule-name-col">{{ $t(`exoplatform.gamification.gamificationinformation.Event`) }}</th>

              <th class="rule-desc-col">
                {{ $t(`exoplatform.gamification.gamificationinformation.domain.Description`) }}
              </th>
              <th class="rule-price-col">{{ $t(`exoplatform.gamification.score`) }}</th>
              <th class="rule-area-col">
                {{ $t(`exoplatform.gamification.gamificationinformation.Domain`) }}
              </th>
              <th class="rule-enable-col">{{ $t(`exoplatform.gamification.enabled`) }}</th>
              <th class="rule-action-col">{{ $t(`exoplatform.gamification.action`) }}</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="rule in filteredRules">
              <td>
                <div>
                  {{
                    $t(`exoplatform.gamification.gamificationinformation.rule.title.${rule.event}`,rule.event)
                  }}
                </div>
              </td>
              <td class="rule-desc-col">
                <div>
                  {{
                    $t(`exoplatform.gamification.gamificationinformation.rule.description.${rule.title}`,rule.description)
                  }}
                </div>
              </td>
              <td>
                <div>{{ rule.score }}</div>
              </td>
              <td style="max-width: 115px;">
                <div v-if="rule.domainDTO != null">
                  {{
                    $t(`exoplatform.gamification.gamificationinformation.domain.${rule.domainDTO.title}`,rule.domainDTO.title)
                  }}
                </div>
              </td><td>
                <div>
                  <label class="switch">
                    <input
                      v-model="rule.enabled"
                      disabled
                      type="checkbox">
                    <div class="slider round"><span class="absolute-yes">{{ $t(`exoplatform.gamification.YES`,"YES") }}</span></div>
                    <span class="absolute-no" data-value="rule.enabled">{{ $t(`exoplatform.gamification.NO`) }}</span>
                  </label>
                </div>
              </td>


              <td class="center actionContainer">
                <a
                  aria-controls="collapseTwo"
                  aria-expanded="true"
                  data-target="#collapseTwo"
                  data-toggle="collapse"
                  href=""
                  @click.prevent="collapseButtonn(rule)"> <i
                    class="uiIconEdit uiIconLightGray"></i></a>
                <a
                  v-b-tooltip.hover
                  class="actionIcon"
                  data-placement="bottom"
                  href="#"
                  rel="tooltip"
                  data-original-title="Supprimer"
                  title="Supprimer"
                  @click.prevent="collapseConfirm(rule)">
                  <i class="uiIconDelete uiIconLightGray"></i>
                </a>
              </td>
            </tr>


            <tr v-if="!rules.length || !filteredRules.length " v-model="search">
              <td class="empty center" colspan="6">
                {{ $t(`exoplatform.gamification.ErrorRulesMsg`) }}
              </td>
            </tr>
          </tbody>
        </table>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
    import Vue from 'vue'
    import BootstrapVue from 'bootstrap-vue'
    import 'bootstrap/dist/css/bootstrap.css'
    import 'bootstrap-vue/dist/bootstrap-vue.css'
    import moment from 'moment'

    Vue.use(BootstrapVue);
Vue.prototype.moment = moment;
export default {
    props: ['rules', 'domains', 'events', 'rule', 'domain'],
    data() {
        return {
            search: '',
            formErrors: {},
            editedrule: {},
            isdeleted: false,
            isShown: false,
            isShowndeleted: false,
            enabledFilter: null,
            isEnabled: false,
            editedEnabled: null,
            enabledMessage:"",
            filerlabel:"all",
        }
    },

    computed: {
        filteredRules() {
            return this.rules.filter(item => {
                return ((
                    this.$t(`exoplatform.gamification.gamificationinformation.rule.description.${item.title}`,item.description).toLowerCase().indexOf(this.search.toLowerCase()) > -1 ||
                    this.$t(`exoplatform.gamification.gamificationinformation.rule.title.${item.event}`,item.event).toLowerCase().indexOf(this.search.toLowerCase()) > -1 ||
                    this.$t(`exoplatform.gamification.gamificationinformation.domain.${item.domainDTO.title}`,item.domainDTO.title).toLowerCase().indexOf(this.search.toLowerCase()) > -1||
                    item.score.toString().toLowerCase().indexOf(this.search.toLowerCase()) > -1)
                    && (this.enabledFilter === null || item.enabled === this.enabledFilter)
                )
            })
        },
        isDisabled() {
            // evaluate whatever you need to determine disabled here...
            this.disabled = false;
            return !this.domain.enabled;
        },

        isEnabled() {
            // evaluate whatever you need to determine disabled here...
            this.disabled = true;
            return this.domain.enabled;
        }        ,
        isBottonDisabled: function(){
             return !(this.isNotEmpty(this.editedrule.event)&&this.isNotEmpty(this.editedrule.score)&&this.editedrule.domainDTO!=null)
        }

    },


    methods: {
        onEdit(rule) {
            this.rule = rule;
            this.editedrule = rule;
        },
        isNotEmpty(str){
              return(str!=null&&str!="")
            },
        onRemove(id, title) {
            this.$emit('remove', id, title);
            this.isdeleted = true
        },
        onSave(rule) {
            this.$emit('save', rule);
            this.editedrule = {};
            if (this.isShown) {
                this.closeAlert(".alert")
            }
        },
        onCancel(rule) {
            this.editedrule = {};
        },
        collapseConfirm(rule) {
            this.rule = rule;
            this.isShowndeleted = !this.isShowndeleted;
            if (this.isShowndeleted) {
                this.closeAlert(".alert")
            }
        },

        onSubmit(rule) {
            this.updateDomain(this.editedrule);
            this.collapseButtonn(rule)
        },
        collapseButtonn(rule) {
            this.editedrule = rule;
            this.isShown = !this.isShown;
        },
        closeAlert(item) {
            setTimeout(function () {
                $(item).fadeOut('fast')
            }, 4000);
        }
    }
}
</script>

<style scoped>
.uiSearchInput.searchWithIcon {
    display: flex;
    flex-direction: row-reverse;
    float: right;
    margin-top: 17px;
}

i.uiIconSearch.uiIconLightGray {
    position: relative;
    float: left;
}

@media (max-width: 416px) {
    .uiSearchInput.searchWithIcon {
        max-width: 45%;
        margin-left: 5px;
    }
}

@media (max-width: 340px) {
    .uiSearchInput.searchWithIcon {
        max-width: 35%;
        margin-left: 5px;

    }
}

.container-fluid {
    display: table;
}

.table {
    position: relative;
    border-radius: 3px;
    background: #fff;
    margin-bottom: 20px;
    width: 96%;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
    margin: 14px auto 0;
    margin-bottom: 30px;
}

.uiGrid.table tr td {
    padding: 5px;
    vertical-align: inherit;
}

.table thead th {
    font-size: 0.9em;
}

.table td,
.table th {
    padding: 8px;
    line-height: 1.42857143;
    vertical-align: top;
    text-align: center;
    border: none;
}

.table-hover tbody tr:hover {
    cursor: pointer;
}

input[type="text"] {
    height: 37px;
    margin: auto;
}

td.rule-needed-score-col input {
    width: 60px;
    text-align: center;
}

td select {
    word-wrap: normal;
    border: 2px Solid #e1e8ee;
    border-radius: 5px;
    margin: auto;
    outline: none;
}

input.rule-desc-col {
    min-width: 98%;
}

/* switch */
.switch {
    position: relative;
    display: inline-block;
    width: 53px;
    height: 32px;
    /* zoom: 30%; */
    top: 0.4rem;
}

.switch input {
    display: none;
}

.slider {
    position: absolute;
    cursor: pointer;
    overflow: hidden;
    top: 5px;
    left: 0;
    right: 0;
    bottom: 0;
    width: 60px;
    height: 20px;
    background-color: #f2f2f2;
    -webkit-transition: .4s;
    transition: .4s;
}

.slider:before {
    position: absolute;
    z-index: 2;
    content: "";
    height: 14px;
    width: 14px;
    left: 5px;
    bottom: 3px;
    background-color: darkgrey;
    -webkit-box-shadow: 0 2px 5px rgba(0, 0, 0, 0.22);
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.22);
    -webkit-transition: .4s;
    transition: all 0.4s ease-in-out;
}


.slider:after {
    position: absolute;
    left: -20px;
    z-index: 1;
    content: "";
    font-size: 13px;
    text-align: left !important;
    line-height: 19px;
    padding-left: 0;
    width: 95px;
    height: 26px !important;
    color: #f9f9f9;
    background-color: #477ab3;
    background-image: -moz-linear-gradient(top, #578dc9, #2f5e92);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#578dc9), to(#2f5e92));
    background-image: -webkit-linear-gradient(top, #578dc9, #2f5e92);
    background-image: -o-linear-gradient(top, #578dc9, #2f5e92);
    background-image: linear-gradient(to bottom, #578dc9, #2f5e92);
    background-repeat: repeat-x;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff578dc9', endColorstr='#ff2f5e92', GradientType=0);
    -webkit-box-shadow: inset 0px 3px 5px #224469;
    -moz-box-shadow: inset 0px 3px 5px #224469;
    box-shadow: inset 0px 3px 5px #224469;
    -webkit-border-top-left-radius: 9px;
    -moz-border-radius-topleft: 9px;
    border-top-left-radius: 9px;
    -webkit-border-bottom-left-radius: 9px;
    -moz-border-radius-bottomleft: 9px;
    border-bottom-left-radius: 9px;
    height: 57px;
    border-radius: 100px;
    background-color: #578dc9;
    -webkit-transform: translateX(-190px);
    -ms-transform: translateX(-190px);
    transform: translateX(-190px);
    transition: all 0.4s ease-in-out;
}

input:checked+.slider:after {
    -webkit-transform: translateX(0px);
    -ms-transform: translateX(0px);
    transform: translateX(0px);
    padding-left: 25px;
}

input:checked+.slider:before {
    background-color: #fff;
    -webkit-transform: translateX(38px);
    -ms-transform: translateX(38px);
    transform: translateX(38px);
}

input:checked+.slider:before {
    -webkit-transform: translateX(38px);
    -ms-transform: translateX(38px);
    transform: translateX(38px);
}

/* Rounded sliders */
.slider.round {
    border-radius: 100px;
}

.slider.round:before {
    border-radius: 50%;
}

.absolute-no {
    position: absolute;
    left: 27px;
    color: DarkGrey;
    text-align: right !important;
    font-size: 16px;
    width: calc(100% - 25px);
    line-height: 30px;
    cursor: pointer;
}

input.rule-needed-score-col {
    max-width: 60px;
    text-align: center;
}

i.uiIconClose.uiIconBlue {
    zoom: 163%;
    vertical-align: super;
    opacity: 1;
    line-height: inherit;
}

i.uiIconSave.uiIconLightGray {
    left: -4px;
}

button.btn.cancel.pull-right {
    border: 1px solid #e1e8ee !important;
    color: #4d5466;
    background: transparent !important;
}


.collapse {
    top: 15px;
}

div#collapseTwo {
    position: fixed;
    z-index: 10000;
    left: 0;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgb(0, 0, 0);
    background-color: rgba(0, 0, 0, 0.4);
}

.alert-success {
    position: absolute;
    top: 11% !important;
    margin: auto 0.5% !important;
}

.filterWithIcon {
    display: flex;
    flex-direction: row-reverse;
    float: right;
    margin-top: 25px;
    margin-right: 8px;
}

.card-body label {
    display: block;
    margin-right: 10px;
}

form {
    margin-bottom: 24px;
}

form-row {
    display: flex;
    flex-wrap: wrap;
    margin-right: -5px;
    margin-left: -5px;
}

div#headingOne button.btn.btn-primary {
    margin: 15px 12px 5px;
}

.btn-primary:focus, .btn-primary.focus {
    box-shadow: inset 0 0 0 0.2rem rgba(38, 143, 255, 0.5);
}

label {
    display: inline-block;
    max-width: 100%;
    margin-bottom: 5px;
    font-weight: 500;
    color: #333;
}

input[type="number"] {
    font-size: 15px;
    height: 40px;
    padding: 0 10px;
    border: 1px solid #e1e8ee;
    border-radius: 5px;
    box-shadow: none;
    max-height: 40px;
    text-overflow: ellipsis;
}

form#areaSelectboxGroup {
    margin-bottom: 0px;
}

.require-msg {
    max-width: 100% !important;
    font-size: 14px;
}

msg.alert-dismissible.alert-danger {
    display: -webkit-inline-box;
    width: auto;
}

input {
    width: 100%;
}

textarea#ruleDescription {
    width: 100%;
    font-size: 15px;
}

button, [type="button"], [type="reset"], [type="submit"] {
    -webkit-appearance: button;
    align-content: stretch;
    padding: 8px 25px;
}

input[type="checkbox"] {
    width: auto;
    margin-bottom: 10px;
}

div#headingOne:hover {
    background: transparent;
    transition: all .5s;
}

div#headingOne {
    box-shadow: none;
    transition: all .5s;
}

button.btn.secondary {
    padding: 8px 25px;
    margin-left: 25px;
    border: 1px solid #e1e8ee !important;
    color: #4d5466;
    background-color: transparent !important;
}

.col-sm-12.card {
    position: relative;
    border-radius: 3px;
    background: #ffffff;
    margin-bottom: 20px;
    width: 100%;
    box-shadow: none;
    margin: 0 auto;
    padding: 0;
    border: none;
}

.collapse {
    top: 15px;
}

div#collapseOne {
    position: fixed;
    z-index: 10000;
    left: 0;
    top: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgb(0, 0, 0);
    background-color: rgba(0, 0, 0, 0.4);
}

/* switch */
.uiSwitchBtn {
    position: relative;
    display: inline-block !important;
    width: 60px;
    height: 29px;
    margin-left: 20px;
}

.uiSwitchBtn input {
    display: none;
}

.slider {
    position: absolute;
    cursor: pointer;
    overflow: hidden;
    top: 5px;
    left: 0;
    right: 0;
    bottom: 0;
    width: 60px;
    height: 20px;
    background-color: #f2f2f2;
    -webkit-transition: .4s;
    transition: .4s;
}

.slider:before {
    position: absolute;
    z-index: 2;
    content: "";
    height: 14px;
    width: 14px;
    left: 5px;
    bottom: 3px;
    background-color: darkgrey;
    -webkit-box-shadow: 0 2px 5px rgba(0, 0, 0, 0.22);
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.22);
    -webkit-transition: .4s;
    transition: all 0.4s ease-in-out;
}

.slider:after {
    position: absolute;
    left: -20px;
    z-index: 1;
    content: "";
    font-size: 13px;
    text-align: left !important;
    line-height: 19px;
    padding-left: 0;
    width: 95px;
    height: 26px !important;
    color: #f9f9f9;
    background-color: #477ab3;
    background-image: -moz-linear-gradient(top, #578dc9, #2f5e92);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#578dc9), to(#2f5e92));
    background-image: -webkit-linear-gradient(top, #578dc9, #2f5e92);
    background-image: -o-linear-gradient(top, #578dc9, #2f5e92);
    background-image: linear-gradient(to bottom, #578dc9, #2f5e92);
    background-repeat: repeat-x;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff578dc9', endColorstr='#ff2f5e92', GradientType=0);
    -webkit-box-shadow: inset 0px 3px 5px #224469;
    -moz-box-shadow: inset 0px 3px 5px #224469;
    box-shadow: inset 0px 3px 5px #224469;
    -webkit-border-top-left-radius: 9px;
    -moz-border-radius-topleft: 9px;
    border-top-left-radius: 9px;
    -webkit-border-bottom-left-radius: 9px;
    -moz-border-radius-bottomleft: 9px;
    border-bottom-left-radius: 9px;
    height: 57px;
    border-radius: 100px;
    background-color: #578dc9;
    -webkit-transform: translateX(-190px);
    -ms-transform: translateX(-190px);
    transform: translateX(-190px);
    transition: all 0.4s ease-in-out;
}
input:not(:checked)+.slider > .absolute-yes {
    z-index: -1;
    position: absolute;
    -webkit-transform: translateX(-190px);
    -ms-transform: translateX(-190px);
    transform: translateX(-190px);
    left: 2px;
    color: white;
    text-align: right;
    font-size: 16px;
    width: calc(100% - 25px);
    line-height: 22px;
    cursor: pointer;
    transition: all 0.4s ease-in-out;
}
input:checked+.slider> .absolute-yes {
    -webkit-transform: translateX(0px);
    -ms-transform: translateX(0px);
    transform: translateX(0px);
    z-index: 15;
    position: absolute;
    left: 2px;
    color: white;
    text-align: right;
    font-size: 16px;
    width: calc(100% - 25px);
    line-height: 22px;
    cursor: pointer;
    transition: all 0.4s ease-in-out;
}
input:checked + .slider:after {
    -webkit-transform: translateX(0px);
    -ms-transform: translateX(0px);
    transform: translateX(0px);
    padding-left: 25px;
}

input:checked + .slider:before {
    background-color: #fff;
    -webkit-transform: translateX(38px);
    -ms-transform: translateX(38px);
    transform: translateX(38px);
}

input:checked + .slider:before {
    -webkit-transform: translateX(38px);
    -ms-transform: translateX(38px);
    transform: translateX(38px);
}

/* Rounded sliders */
.slider.round {
    border-radius: 100px;
}

.slider.round:before {
    border-radius: 50%;
}

.absolute-no {
    position: absolute;
    left: 27px;
    color: DarkGrey;
    text-align: right !important;
    font-size: 16px;
    /* width: calc(100% - 25px); */
    width: auto;
    line-height: 30px;
    cursor: pointer;
}

input.rule-needed-score-col {
    max-width: 60px;
    text-align: center;
}

i.uiIconClose.uiIconBlue {
    zoom: 133%;
    height: 100%;
    vertical-align: super;
    color: #578dc9;
    line-height: inherit;
}

.collapse.show.out {
    display: none;
}

.uiPopup .popupContent select, .modal.uiPopup .popupContent select {
    outline: none;
    border: 2px solid #e1e8ee;
    border-radius: 5px;
    box-shadow: none;
    margin-left: 20px;
    height:40px;
    width:352px;
    margin-top: -7px;
}

select:focus {
    border-color: #a6bad6;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 5px #c9d5e6;
    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 5px #c9d5e6;
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 5px #c9d5e6;
    color: #333;
}


select.mb-4.select-event {
    margin: 0 !important;
    width: 100%;
    height: 40px;
}

.action-bar.dropdown.filterWithIcon> a.actionIcon.dropdown-toggle {
    box-shadow: none;
    border: none;
    text-decoration: none;
    margin: auto;
    width: 100%;
    border-radius: 3px;
    background-color: transparent;
}
form.switchEnabled, form#domainSelectboxGroup {
    display: inline-flex;
    margin: 20px 5px;
}
button.btn-primary.pull-right {
    border-radius: 0.25rem;
    height: 44px;
}

    .filter-bar{
    display: flex;
    flex-direction: row-reverse;
    float: right;
    margin-top: 17px;
    margin-right: 12px;
    }
.filter-bar select.mb-4 {
    word-wrap: normal;
    border: 2px Solid #e1e8ee;
    border-radius: 5px;
    margin: auto;
    outline: none;
    height: 36px;
    width: auto;
    padding: 0 10px;
}
    .btn-primary.disabled, .btn-primary:disabled {
     background-color: #afc9e5; 
     background-image: none;
}
.PopupContent.popupContent select {
    height: 40px;
}
form.switchEnabled label.switch {
    margin-left: 21px;
    margin-top: -7px;
}

</style>
