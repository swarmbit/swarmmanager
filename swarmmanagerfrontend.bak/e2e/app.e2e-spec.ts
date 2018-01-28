import { SwarmmanagerfrontendPage } from './app.po';

describe('swarmmanagerfrontend App', () => {
  let page: SwarmmanagerfrontendPage;

  beforeEach(() => {
    page = new SwarmmanagerfrontendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
