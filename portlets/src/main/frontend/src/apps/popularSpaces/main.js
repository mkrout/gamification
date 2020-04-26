import './initComponents.js';

//get overrided components if exists
if (extensionRegistry) {
  const components = extensionRegistry.loadComponents('PopularSpaces');
  if (components && components.length > 0) {
   components.forEach(cmp => {
     Vue.component(cmp.componentName, cmp.componentOptions);
   });
  }
}

document.dispatchEvent(new CustomEvent('displayTopBarLoading'));

Vue.use(Vuetify);

const vuetify = new Vuetify({
    dark: true,
    iconfont: '',
});

// getting language of user
const lang = eXo && eXo.env && eXo.env.portal && eXo.env.portal.language || 'en';

const resourceBundleName = 'locale.addon.Gamification';
const url = `${eXo.env.portal.context}/${eXo.env.portal.rest}/i18n/bundle/${resourceBundleName}-${lang}.json`;
const appId = 'popularSpacesApplication';

export function init(period, limit) {
  //getting locale ressources
  exoi18n.loadLanguageAsync(lang, url)
    .then(i18n => {
        // init Vue app when locale ressources are ready
        new Vue({
          template: `<popular-spaces id="${appId}" period="${period}" limit="${limit}" />`,
          i18n,
          vuetify,
        }).$mount(`#${appId}`);
    });
}